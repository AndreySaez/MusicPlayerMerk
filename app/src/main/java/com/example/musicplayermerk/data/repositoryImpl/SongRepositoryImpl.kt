package com.example.musicplayermerk.data.repositoryImpl

import com.example.musicplayermerk.data.provider.SongProvider
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songProvider: SongProvider
) : SongRepository {

    override suspend fun getSongList(): List<Song> = withContext(Dispatchers.IO) {
        songProvider.getSongs().songs
    }
}
