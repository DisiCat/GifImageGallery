package com.example.gifimagegallery.requesters

import com.example.gifimagegallery.constants.AppDefaultValues
import com.example.gifimagegallery.network.RestApiService
import com.example.gifimagegallery.network.parseModels.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GIFsRequester @Inject constructor(
    private val restApiService: RestApiService
) : IGIFsRequester {
    override suspend fun sendRequest(searchKey: String, offset: Int): Response<DataModel>? {
        return try {
            withContext(Dispatchers.IO) {
                val url = "https://api.giphy.com/v1/gifs/search?api_key=YGHnKKBGSydS6nSt6WA\n" +
                        "oUcICWwmgCfvL&amp;q=${searchKey}&amp;limit=${AppDefaultValues.DEFAULT_ITEMS_LOAD}&amp;offset=${offset}&amp;rating=g&amp;lang=en"
                return@withContext restApiService.getGIFs(url)
            }
        } catch (e: Exception) {
            return e.message?.toResponseBody()?.let { Response.error(e.hashCode(), it) }
        }
    }
}