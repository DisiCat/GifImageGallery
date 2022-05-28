package com.example.gifimagegallery.network

import com.example.gifimagegallery.network.parseModels.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RestApiService {
    @GET
    suspend fun getGIFs(@Url url: String): Response<DataModel>
}