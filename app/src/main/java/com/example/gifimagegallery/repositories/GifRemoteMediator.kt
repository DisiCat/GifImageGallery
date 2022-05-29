package com.example.gifimagegallery.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gifimagegallery.constants.AppDefaultValues
import com.example.gifimagegallery.db.GifDatabase
import com.example.gifimagegallery.db.RemoteKeys
import com.example.gifimagegallery.network.parseModels.GifModel
import com.example.gifimagegallery.requesters.IGIFsRequester
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator @AssistedInject constructor(
    private val requester: IGIFsRequester,
    @Assisted("searchValue") private val searchValue: String,
    private val gifDatabase: GifDatabase
) : RemoteMediator<Int, GifModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifModel>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: AppDefaultValues.INITIAL_PAGE_NUMBER
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKeys = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKeys
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val offset = (page - 1) * AppDefaultValues.DEFAULT_ITEMS_LOAD

        try {
            val response = requester.sendRequest(searchValue, offset)
            val items = response?.body()?.data ?: emptyList()
            val endOfPaginationReached = items.isEmpty()

            gifDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gifDatabase.remoteKeysDao().clearRemoteKeys()
                    gifDatabase.gifsDao().clearGifs()
                }

                val prevKey = if (page == AppDefaultValues.INITIAL_PAGE_NUMBER) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys =
                    items.map { RemoteKeys(gifId = it.id, prevKey = prevKey, nextKey = nextKey) }
                gifDatabase.remoteKeysDao().insertAll(keys)
                gifDatabase.gifsDao().insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GifModel>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { gif ->
            gifDatabase.remoteKeysDao().remoteKeyGifId(gifId = gif.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GifModel>): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { gif ->
            gifDatabase.remoteKeysDao().remoteKeyGifId(gifId = gif.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GifModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.id?.let { gifId ->
                gifDatabase.remoteKeysDao().remoteKeyGifId(gifId)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("searchValue") searchValue: String): GifRemoteMediator
    }
}

