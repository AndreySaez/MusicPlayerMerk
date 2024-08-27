package com.example.musicplayermerk.presentation.di.playerdi

import androidx.activity.ComponentActivity
import com.example.musicplayermerk.di.AppDependencies
import com.example.musicplayermerk.presentation.di.FragmentScope
import com.example.musicplayermerk.presentation.view.player.PlayerFragment
import com.example.musicplayermerk.presentation.viewmodel.playerViewModel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [PlayerFragmentModule::class]
)
@FragmentScope
interface PlayerComponent {
    fun inject(fragment: PlayerFragment)
    fun vmFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppDependencies,
            @BindsInstance activity: ComponentActivity
        ): PlayerComponent
    }
}