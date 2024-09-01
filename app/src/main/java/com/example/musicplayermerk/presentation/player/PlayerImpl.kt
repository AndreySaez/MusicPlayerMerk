package com.example.musicplayermerk.presentation.player

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.musicplayermerk.domain.Player
import com.example.musicplayermerk.domain.PlayerState
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.presentation.playerService.Constants
import com.example.musicplayermerk.presentation.playerService.PlayerNotificationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerImpl @Inject constructor(
    private val context: Context
) : Player {
    private val exoPlayer by lazy {
        ExoPlayer.Builder(context)
            .build().also { player ->
                initPlayer(player)
            }
    }

    private val scope = CoroutineScope(Dispatchers.Default)
    private var currentJob: Job? = null
    private val currentPosition get() = exoPlayer.currentPosition
    private val duration get() = exoPlayer.duration


    override val nowPlaying: Flow<PlayerState>
        get() = _nowPlaying
            .asStateFlow()
            .filterNotNull()

    private var _nowPlaying = MutableStateFlow<PlayerState?>(null)
    override var songs: List<Song> = emptyList()
        set(value) {
            setSongsToPlayer(value)
            field = value
        }

    private fun collectPlayingInfo(player: androidx.media3.common.Player) {
        player.currentMediaItem?.localConfiguration?.uri?.let {
            updatePlayingInfo(it, player.isPlaying)
        }
    }

    private fun updatePlayingInfo(currentTrack: Uri, isPlaying: Boolean) {
        val currentSong = songs.find { it.uri == currentTrack } ?: return
        val playerState = PlayerState(
            song = currentSong,
            currentTime = currentPosition,
            songDuration = duration,
            isPlaying = isPlaying
        )
        _nowPlaying.value = playerState
        subscribeToCurrentTime()
    }

    private fun subscribeToCurrentTime() {
        if (!exoPlayer.isPlaying) {
            currentJob?.cancel()
            currentJob = null
            return
        }
        if (currentJob != null) return
        currentJob = scope.launch {
            while (isActive) {
                val currentPosition = withContext(Dispatchers.Main) {
                    currentPosition
                }
                delay(200)
                val currentValue = _nowPlaying.value ?: continue
                _nowPlaying.value = currentValue.copy(
                    currentTime = currentPosition
                )
            }
        }
    }

    private fun setSongsToPlayer(songs: List<Song>) {
        songs.map {
            MediaItem.fromUri(it.uri)
        }.also {
            exoPlayer.setMediaItems(it)
            exoPlayer.prepare()
        }
    }

    override fun play() {
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun next() {
        if (exoPlayer.hasNextMediaItem()) {
            exoPlayer.seekToNext()
        } else {
            exoPlayer.seekToDefaultPosition(0)
        }
    }

    override fun previous() {
        if (exoPlayer.hasPreviousMediaItem()) {
            exoPlayer.seekToPrevious()
        } else {
            exoPlayer.seekToDefaultPosition(exoPlayer.mediaItemCount - 1)
        }
    }

    override fun seekTo(progress: Int) {
        exoPlayer.seekTo(progress.toLong())
    }

    override fun seekToMediaItem(mediaItem: Song, forcePlay: Boolean) {
        val newSongIndex = songs.indexOf(mediaItem)
        if (newSongIndex == -1) return
        if (exoPlayer.currentMediaItemIndex == newSongIndex && exoPlayer.isPlaying) {
            exoPlayer.pause()
            return
        }
        if (exoPlayer.currentMediaItemIndex != newSongIndex) {
            exoPlayer.seekToDefaultPosition(newSongIndex)
        }
        if (forcePlay && !exoPlayer.isPlaying) {
            exoPlayer.play()
        }

    }

    override fun destroy() {
        exoPlayer.release()
    }

    @OptIn(UnstableApi::class)
    override fun setupNotification(listener: PlayerNotificationListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
        val notificationManager = PlayerNotificationManager.Builder(
            context,
            Constants.NOTIFICATION_ID,
            Constants.NOTIFICATION_CHANNEL_ID
        ).setNotificationListener(object : PlayerNotificationManager.NotificationListener {
            @SuppressLint("ForegroundServiceType")
            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                listener.onNotificationPosted(notificationId, notification, ongoing)
            }

            override fun onNotificationCancelled(
                notificationId: Int,
                dismissedByUser: Boolean
            ) {
                listener.onNotificationCancelled(notificationId, dismissedByUser)
            }
        }).build()
        notificationManager.apply {
            setUseFastForwardAction(false)
            setUseRewindAction(false)
            setColorized(true)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setPriority(NotificationCompat.PRIORITY_MAX)
            setPlayer(exoPlayer)
        }
    }

    private fun initPlayer(player: ExoPlayer) {
        player.addListener(
            object : androidx.media3.common.Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    player.currentMediaItem?.localConfiguration?.uri?.let {
                        updatePlayingInfo(it, isPlaying)
                    }
                }

                override fun onEvents(
                    player: androidx.media3.common.Player,
                    events: androidx.media3.common.Player.Events
                ) {
                    val metadataChanged = events.contains(
                        androidx.media3.common.Player.EVENT_MEDIA_METADATA_CHANGED
                    )
                    val isPlayingChanged = events.contains(
                        androidx.media3.common.Player.EVENT_IS_PLAYING_CHANGED
                    )
                    if (metadataChanged || isPlayingChanged) {
                        collectPlayingInfo(player)
                    }
                }
            }
        )
    }
}