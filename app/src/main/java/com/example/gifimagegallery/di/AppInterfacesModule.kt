package com.example.gifimagegallery.di

import com.example.gifimagegallery.repositories.ISearchRepository
import com.example.gifimagegallery.repositories.SearchRepository
import com.example.gifimagegallery.requesters.GIFsRequester
import com.example.gifimagegallery.requesters.IGIFsRequester
import com.example.gifimagegallery.usecases.ISearchUseCase
import com.example.gifimagegallery.usecases.SearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfacesModule {
    // Repository
    @Binds
    abstract fun bindSearchRepository(impl: SearchRepository): ISearchRepository

    // UseCases
    @Binds
    abstract fun bindSearchUseCase(impl: SearchUseCase): ISearchUseCase

    // Requesters
    @Binds
    abstract fun bindGIFsRequester(impl: GIFsRequester): IGIFsRequester
}