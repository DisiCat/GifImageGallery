package com.example.gifimagegallery.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.Data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val _GIFsPagingSource : GIFsPagingSource.Factory
) : ISearchRepository {
    override fun getSearchResult(query: String): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {_GIFsPagingSource.create(query)}
        ).flow
    }
    companion object{
        const val NETWORK_PAGE_SIZE = 25
    }
}