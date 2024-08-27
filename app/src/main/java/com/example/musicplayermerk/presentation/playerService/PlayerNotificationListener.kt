package com.example.musicplayermerk.presentation.playerService

import android.app.Notification

interface PlayerNotificationListener {
    fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    )

    fun onNotificationCancelled(
        notificationId: Int,
        dismissedByUser: Boolean
    )
}