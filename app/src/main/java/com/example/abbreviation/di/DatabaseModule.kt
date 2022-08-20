package com.example.abbreviation.di

import android.content.Context
import androidx.room.Room
import com.example.abbreviation.data.room.LongFormDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, LongFormDatabase::class.java,"LongFormDatabase"
    ).build()


    @Provides
    fun provideBeerDao(database: LongFormDatabase) = database.longFormDao()
}