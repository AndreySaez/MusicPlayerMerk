package com.example.musicplayermerk.presentation.di.playerdi

import com.example.musicplayermerk.domain.Player
import com.example.musicplayermerk.presentation.player.PlayerImpl
import dagger.Binds
import dagger.Module

@Module
interface PlayerServiceModule {
    @Binds
    fun bindPlayer(impl: PlayerImpl): Player
}