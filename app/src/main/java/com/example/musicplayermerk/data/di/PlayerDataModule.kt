package com.example.musicplayermerk.data.di

import com.example.musicplayermerk.data.repositoryImpl.SongRepositoryImpl
import com.example.musicplayermerk.domain.repository.SongRepository
import dagger.Binds
import dagger.Module

@Module
interface PlayerDataModule {
    @Binds
    fun bindRepository(songRepositoryImpl: SongRepositoryImpl): SongRepository
}