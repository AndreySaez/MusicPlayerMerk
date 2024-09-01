package com.example.musicplayermerk.presentation.viewmodel.playerViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermerk.domain.Player
import com.example.musicplayermerk.domain.PlayerState
import com.example.musicplayermerk.domain.repository.SongRepository
import com.example.musicplayermerk.presentation.playerService.PlayerConnectionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val songRepository: SongRepository,
    playerManager: PlayerConnectionManager,
) : ViewModel() {

    private var player: Player? = null

    private var isSongsLoaded = false
    val currentState: Flow<PlayerState> get() = playerFlow.flatMapLatest { it.nowPlaying }
    private val playerFlow = playerManager.playerFlow

    init {
        playerFlow.flatMapLatest {
            player = it
            flow<Unit> {
                if (isSongsLoaded) return@flow
                player?.songs = songRepository.getSongList()
                isSongsLoaded = true
            }
        }.launchIn(viewModelScope)
    }

    fun changePosition(progress: Int) {
        player?.seekTo(progress)
    }

    fun play() {
        player?.play()
    }

    fun stop() {
        player?.pause()
    }

    fun next() {
        player?.next()
    }

    fun previous() {
        player?.previous()
    }
}