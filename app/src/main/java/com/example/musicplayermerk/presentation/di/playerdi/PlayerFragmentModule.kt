package com.example.musicplayermerk.presentation.di.playerdi

import androidx.lifecycle.ViewModel
import com.example.musicplayermerk.presentation.di.FragmentScope
import com.example.musicplayermerk.presentation.di.ViewModelKey
import com.example.musicplayermerk.presentation.viewmodel.playerViewModel.PlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PlayerFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    @FragmentScope
    fun bindPlayerViewModel(playerViewModel: PlayerViewModel): ViewModel

}