package com.example.gifimagegallery.usecases

import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.GifModel
import kotlinx.coroutines.flow.Flow


interface ISearchUseCase {
    fun getSearchResult(query: String): Flow<PagingData<GifModel>>
   suspend fun deleteGifById(gifId: String)
}
