package com.example.musicplayermerk.presentation.di.songslistdi

import androidx.lifecycle.ViewModel
import com.example.musicplayermerk.presentation.di.FragmentScope
import com.example.musicplayermerk.presentation.di.ViewModelKey
import com.example.musicplayermerk.presentation.viewmodel.songslistviewmodel.SongListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SongsListFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(SongListViewModel::class)
    @FragmentScope
    fun bindSongsListViewModel(songListViewModel: SongListViewModel): ViewModel
}