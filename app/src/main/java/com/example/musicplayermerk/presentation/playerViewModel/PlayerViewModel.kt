package com.example.musicplayermerk.presentation.playerViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermerk.domain.repository.SongRepository
import com.example.musicplayermerk.presentation.state.ListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    private val _songList = MutableStateFlow<ListState>(ListState.Empty)
    val songList: StateFlow<ListState> = _songList.asStateFlow()

    init {
        viewModelScope.launch {
            _songList.value = ListState.SongList(songRepository.getSongList())
        }
    }
}