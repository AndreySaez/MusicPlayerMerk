package com.example.musicplayermerk.presentation.playerService

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.musicplayermerk.appComponent
import com.example.musicplayermerk.domain.Player
import javax.inject.Inject

class PlayerService : Service() {
    private val binder = MusicBinder()

    @Inject
    lateinit var player: Player

    override fun onCreate() {
        super.onCreate()
        startService(Intent(this, PlayerService::class.java))
        applicationContext.appComponent.inject(this)
        player.setupNotification(object : PlayerNotificationListener {
            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                if (!ongoing) {
                    stopForeground(STOP_FOREGROUND_DETACH)
                } else {
                    startForeground(notificationId, notification)
                }
            }

            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                stopSelf()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        player.destroy()
    }

    override fun onBind(intent: Intent?): IBinder = binder

    inner class MusicBinder : Binder() {
        fun getService(): PlayerService = this@PlayerService
    }


}