package com.example.musicplayermerk.presentation.playerService

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.eventFlow
import androidx.lifecycle.lifecycleScope
import com.example.musicplayermerk.domain.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PlayerConnectionManager @Inject constructor(private val activity: ComponentActivity) {
    val player: Player get() = if (bound) {
        service.player
    } else {
        throw IllegalStateException("Access to player without connected service")
    }

    val playerFlow: Flow<Player> get() = flow {
        val player = asyncPlayer()
        emit(player)
    }.filterNotNull()
    private suspend fun asyncPlayer(): Player? = suspendCoroutine { continuation ->
        if (bound) {
            continuation.resume(player)
        } else {
             object : ServiceListener {
                override fun invoke(isBound: Boolean) {
                    if (isBound) {
                        continuation.resume(player)
                    } else {
                        continuation.resume(null)
                    }
                    connectionListeners.remove(this)
                }
            }.also(connectionListeners::add)
        }
    }

    private lateinit var service: PlayerService
    private var bound: Boolean = false

    private var connectionListeners: MutableSet<ServiceListener> = mutableSetOf()

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            Toast.makeText(activity, "SERVICE IS BOUND", Toast.LENGTH_LONG).show()
            val binder = iBinder as PlayerService.MusicBinder
            service = binder.getService()
            bound = true
            connectionListeners.toList().forEach {
                it.invoke(bound)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Toast.makeText(activity, "SERVICE IS UNBOUND", Toast.LENGTH_LONG).show()
            bound = false
            connectionListeners.toList().forEach {
                it.invoke(bound)
            }
        }
    }

    init {
        activity.lifecycle.eventFlow.onEach {
            when (it) {
                Lifecycle.Event.ON_START -> {
                    startConnection()
                }

                Lifecycle.Event.ON_STOP -> {
                    finishConnection()
                }

                else -> Unit
            }
        }.launchIn(activity.lifecycleScope)
    }

    private fun startConnection() {
        Intent(activity, PlayerService::class.java).also {
            activity.bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun finishConnection() {
        activity.unbindService(serviceConnection)
    }

    interface ServiceListener: (Boolean) -> Unit
}