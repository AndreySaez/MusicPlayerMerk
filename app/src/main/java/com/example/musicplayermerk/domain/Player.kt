package com.example.musicplayermerk.domain

import kotlinx.coroutines.flow.Flow

interface Player {

    val nowPlaying: Flow<PlayerState>

    var songs: List<Song>
    fun play()

    fun pause()

    fun next()
    fun previous()

}

data class PlayerState(
    val song: Song,
    val isPlaying: Boolean
)