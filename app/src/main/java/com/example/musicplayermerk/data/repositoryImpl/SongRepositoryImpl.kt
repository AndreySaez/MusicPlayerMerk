package com.example.musicplayermerk.data.repositoryImpl

import com.example.musicplayermerk.data.SongDataSource
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDataSource: SongDataSource
) : SongRepository {

    override suspend fun getSongList(): List<Song> = withContext(Dispatchers.IO) {
        songDataSource.getSongsData().songs
    }
}
