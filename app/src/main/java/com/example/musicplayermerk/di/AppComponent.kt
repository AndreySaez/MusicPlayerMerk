package com.example.musicplayermerk.di

import com.example.musicplayermerk.data.di.PlayerDataModule
import com.example.musicplayermerk.presentation.di.PlayerPresentationModule
import com.example.musicplayermerk.presentation.view.PlayerFragment
import dagger.Component

@Component(
    modules = [
        PlayerDataModule::class,
        PlayerPresentationModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: PlayerFragment)
}