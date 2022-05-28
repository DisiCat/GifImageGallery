package com.example.gifimagegallery.requesters

import com.example.gifimagegallery.network.parseModels.DataModel
import retrofit2.Response

interface IGIFsRequester {
    suspend fun sendRequest(searchValue: String, offset: Int): Response<DataModel>?
}
