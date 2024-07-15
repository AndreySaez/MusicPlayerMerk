package com.example.musicplayermerk.data.di

import com.example.musicplayermerk.data.provider.SongProvider
import com.example.musicplayermerk.data.provider.SongProviderImpl
import com.example.musicplayermerk.data.repositoryImpl.SongRepositoryImpl
import com.example.musicplayermerk.domain.repository.SongRepository
import dagger.Binds
import dagger.Module

@Module(includes = [PlayerDataModule.Declarations::class])
class PlayerDataModule {
    @Module
    abstract class Declarations {
        @Binds
        abstract fun bindRepository(songRepositoryImpl: SongRepositoryImpl): SongRepository
        @Binds
        abstract fun bindProvider(songProviderImpl: SongProviderImpl): SongProvider
    }
}