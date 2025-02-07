package com.ana.core.di

import android.content.Context
import androidx.room.Room
import com.ana.core.data.source.local.room.FavoriteDao
import com.ana.core.data.source.local.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context:Context): FavoriteDatabase =
        Room.databaseBuilder(
            context, FavoriteDatabase::class.java, "favorite.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase): FavoriteDao =
        favoriteDatabase.favoriteDao()
}