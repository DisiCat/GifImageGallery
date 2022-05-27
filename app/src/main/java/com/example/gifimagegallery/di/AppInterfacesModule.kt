package com.example.gifimagegallery.di

import com.example.gifimagegallery.requesters.GIFsRequester
import com.example.gifimagegallery.requesters.IGIFsRequester
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfacesModule {

    // Requesters
    @Binds
    abstract fun bindGIFsRequester(impl: GIFsRequester): IGIFsRequester
}