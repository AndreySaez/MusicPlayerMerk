package com.example.musicplayermerk.di

import android.content.Context
import com.example.musicplayermerk.data.di.PlayerDataModule
import com.example.musicplayermerk.presentation.di.PlayerPresentationModule
import com.example.musicplayermerk.presentation.view.PlayerFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        PlayerDataModule::class,
        PlayerPresentationModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: PlayerFragment)
}