package com.example.gifimagegallery.usecases

import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.GifModel
import com.example.gifimagegallery.repositories.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUseCase @Inject constructor(
    private val searchRepository: ISearchRepository
): ISearchUseCase {
    override fun getSearchResult(query: String): Flow<PagingData<GifModel>>{
        return searchRepository.getSearchResult(query)
    }
}