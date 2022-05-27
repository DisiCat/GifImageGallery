package com.example.gifimagegallery.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gifimagegallery.constants.AppDefaultValues.INITIAL_PAGE_NUMBER
import com.example.gifimagegallery.network.parseModels.GIFsModel
import com.example.gifimagegallery.requesters.IGIFsRequester
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class GIFsPagingSource @AssistedInject constructor(
    private val requester: IGIFsRequester,
    @Assisted("searchValue") private val searchValue: String
) : PagingSource<Int, GIFsModel>() {
    override fun getRefreshKey(state: PagingState<Int, GIFsModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GIFsModel> {
        if (searchValue.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val response = requester.sendRequest(searchValue, pageNumber)

            return if (response == null || !response.isSuccessful) {
                LoadResult.Error(HttpException(response))
            } else {
                val items = response.body()?.data ?: emptyList()
                val nextPageNumber = if (items.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                LoadResult.Page(items,prevPageNumber,nextPageNumber)
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)

        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}