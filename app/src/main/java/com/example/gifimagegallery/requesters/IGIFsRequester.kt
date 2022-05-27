package com.example.gifimagegallery.requesters

import com.example.gifimagegallery.network.parseModels.DataParseModel
import retrofit2.Response

interface IGIFsRequester {
    suspend fun sendRequest(searchString: String, offset: Int): Response<DataParseModel>?
}
