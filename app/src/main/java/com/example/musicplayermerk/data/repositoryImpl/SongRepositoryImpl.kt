package com.example.musicplayermerk.data.repositoryImpl

import android.content.Context
import com.example.musicplayermerk.data.SongDataSource
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.domain.UriParse
import com.example.musicplayermerk.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDataSource: SongDataSource,
    private val context: Context
) : SongRepository {

    override suspend fun getSongList(): List<Song> = withContext(Dispatchers.IO) {
        songDataSource.getSongsData().songs.onEach {
            it.uri = UriParse.uriFromSong(context, it)
        }
    }
}
