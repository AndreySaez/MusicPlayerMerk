package com.example.musicplayermerk.presentation.viewmodel.songslistviewmodel

import androidx.lifecycle.ViewModel
import com.example.musicplayermerk.domain.Song
import com.example.musicplayermerk.presentation.playerService.PlayerConnectionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    playerManager: PlayerConnectionManager
) : ViewModel() {
    private val playerFlow = playerManager.playerFlow

    val currentState: Flow<ViewState> = playerFlow.flatMapLatest { player ->
        player.nowPlaying
            .map {
                ViewState(player.songs, it.song)
            }
            .distinctUntilChanged()
    }

    data class ViewState(
        val songs: List<Song>,
        val currentSong: Song
    )
}