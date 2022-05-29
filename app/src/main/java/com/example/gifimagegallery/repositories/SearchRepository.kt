package com.example.gifimagegallery.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gifimagegallery.db.GifDatabase
import com.example.gifimagegallery.db.RemoteImage
import com.example.gifimagegallery.network.parseModels.GifModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    //private val _GIFsPagingSource: GIFsPagingSource.Factory
    private val database: GifDatabase,
    private val gifRemoteMediator: GifRemoteMediator.Factory
) : ISearchRepository {
    override fun getSearchResult(searchValue: String): Flow<PagingData<GifModel>> {
        val dbQuery = "%${searchValue.replace(' ', '%')}%"
        val pagingSourceFactory = { database.gifsDao().getGifsBySearch(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = gifRemoteMediator.create(searchValue = searchValue),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    override suspend fun deleteImageById(gifId: String) {
        database.gifsDao().deleteGifById(gifId)
        database.remoteImageDao().insertRemoveImage(RemoteImage(gifId))
    }
}