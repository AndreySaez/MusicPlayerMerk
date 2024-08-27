package com.example.musicplayermerk.di

import android.content.Context
import com.example.musicplayermerk.data.di.PlayerDataModule
import com.example.musicplayermerk.domain.repository.SongRepository
import com.example.musicplayermerk.presentation.di.playerdi.PlayerServiceModule
import com.example.musicplayermerk.presentation.playerService.PlayerService
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        PlayerDataModule::class,
        PlayerServiceModule::class
    ]
)
interface AppComponent: AppDependencies {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(service: PlayerService)
}

interface AppDependencies {
    fun repository(): SongRepository
}