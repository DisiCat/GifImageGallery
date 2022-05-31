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

        val job = withContext(Dispatchers.IO) {
            return@withContext restApiService.getGIFs(
                q = searchKey,
                limit = AppDefaultValues.LIMIT_ITEM_LOAD,
                offset = offset,
                rating = AppDefaultValues.RATING_G,
                lang = AppDefaultValues.DEFAULT_LANG
            )
        }
        return job
    }
}