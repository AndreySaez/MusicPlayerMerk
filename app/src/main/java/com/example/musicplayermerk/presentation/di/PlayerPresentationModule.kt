package com.example.musicplayermerk.presentation.di

import androidx.lifecycle.ViewModel
import com.example.musicplayermerk.presentation.playerViewModel.PlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface PlayerPresentationModule {
    @Binds
    @IntoSet
    fun bindPlayerViewModel(playerViewModel: PlayerViewModel): ViewModel
}