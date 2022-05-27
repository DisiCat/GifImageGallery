package com.example.gifimagegallery.di

import com.example.gifimagegallery.constants.LinkData
import com.example.gifimagegallery.network.RestApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl(LinkData.BASE_URL)
            .client(okHttpClient).addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideRestApiService(retrofit: Retrofit): RestApiService = retrofit.create(RestApiService::class.java)
}