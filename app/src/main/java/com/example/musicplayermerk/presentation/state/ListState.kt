package com.example.musicplayermerk.presentation.state

import com.example.musicplayermerk.domain.Song

sealed class ListState {
    data class SongList(val items: List<Song>) : ListState()

    data object Empty : ListState()
}