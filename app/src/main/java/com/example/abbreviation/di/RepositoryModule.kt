package com.example.abbreviation.di

import com.example.abbreviation.data.repository.Repository
import com.example.abbreviation.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getRepository(repositoryImpl: RepositoryImpl): Repository
}