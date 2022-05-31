package com.example.gifimagegallery.network

import com.example.gifimagegallery.constants.AppDefaultValues
import com.example.gifimagegallery.network.parseModels.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RestApiService {
    @GET("v1/gifs/search?api_key=${AppDefaultValues.API_KEY}")
    suspend fun getGIFs(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") lang: String
    ): Response<DataModel>
}