package com.example.gifimagegallery.repositories

import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.Data
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getSearchResult(query : String) : Flow<PagingData<Data>>
}
