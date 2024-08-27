package com.example.musicplayermerk.presentation.di.songslistdi

import androidx.activity.ComponentActivity
import com.example.musicplayermerk.di.AppDependencies
import com.example.musicplayermerk.presentation.di.FragmentScope
import com.example.musicplayermerk.presentation.view.songslist.SongsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [SongsListFragmentModule::class]
)
@FragmentScope
interface SongsListComponent {
    fun inject(fragment: SongsListFragment)
    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppDependencies,
            @BindsInstance activity: ComponentActivity
        ): SongsListComponent
    }
}