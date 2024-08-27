package com.example.musicplayermerk.domain

import com.example.musicplayermerk.presentation.playerService.PlayerNotificationListener
import kotlinx.coroutines.flow.Flow

interface Player {

    val nowPlaying: Flow<PlayerState>

    var songs: List<Song>
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun destroy()

    fun setupNotification(listener: PlayerNotificationListener)
}

data class PlayerState(
    val song: Song,
    val currentTime: Long,
    val songDuration: Long,
    val isPlaying: Boolean
)