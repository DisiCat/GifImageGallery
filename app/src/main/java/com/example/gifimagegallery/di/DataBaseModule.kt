package com.example.gifimagegallery.di

import android.content.Context
import com.example.gifimagegallery.db.dao.GifDao
import com.example.gifimagegallery.db.GifDatabase
import com.example.gifimagegallery.db.dao.RemoteImageDao
import com.example.gifimagegallery.db.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideGifDataBase(@ApplicationContext context: Context): GifDatabase {
        return GifDatabase.getInstance(context)
    }

    @Provides
    fun provideGifDao(gifDatabase: GifDatabase): GifDao {
        return gifDatabase.gifsDao()
    }

    @Provides
    fun provideRemoteKeysDao(gifDataBase: GifDatabase): RemoteKeysDao {
        return gifDataBase.remoteKeysDao()
    }

    @Provides
    fun provideRemoteImage(gifDatabase: GifDatabase): RemoteImageDao {
        return gifDatabase.remoteImageDao()
    }
}