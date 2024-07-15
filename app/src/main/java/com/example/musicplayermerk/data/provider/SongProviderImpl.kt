package com.example.musicplayermerk.data.provider

import com.example.musicplayermerk.data.SongDataSource
import javax.inject.Inject

class SongProviderImpl @Inject constructor() : SongProvider {
    override suspend fun getSongs(): SongDataSource {
        return SongDataSource()
    }
}