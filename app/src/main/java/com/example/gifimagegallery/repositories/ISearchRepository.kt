package com.example.gifimagegallery.repositories

import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.GifModel
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getSearchResult(searchValue: String): Flow<PagingData<GifModel>>
    suspend fun deleteImageById(gifId : String)
}
