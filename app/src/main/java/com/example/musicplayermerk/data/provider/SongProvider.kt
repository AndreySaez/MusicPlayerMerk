package com.example.musicplayermerk.data.provider

import com.example.musicplayermerk.data.SongDataSource

interface SongProvider {
    suspend fun getSongs():SongDataSource
}