package com.example.musicplayermerk.presentation.playerViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermerk.domain.Player
import com.example.musicplayermerk.domain.PlayerState
import com.example.musicplayermerk.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val player: Player,
) : ViewModel() {
    val currentState: Flow<PlayerState> get() = player.nowPlaying

    init {
        viewModelScope.launch {
            player.songs = songRepository.getSongList()
        }
    }

    fun play() {
        player.play()
    }

    fun stop() {
        player.pause()
    }

    fun next() {
        player.next()
    }

    fun previous() {
        player.previous()
    }
}