package com.example.musicplayermerk.domain.repository

import com.example.musicplayermerk.domain.Song

interface SongRepository {
    suspend fun getSongList(): List<Song>
}