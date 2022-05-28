package com.example.gifimagegallery.usecases

import androidx.paging.PagingData
import com.example.gifimagegallery.network.parseModels.Data
import com.example.gifimagegallery.repositories.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUseCase @Inject constructor(
    private val searchRepository: ISearchRepository
): ISearchUseCase {
    override fun getSearchResult(query: String): Flow<PagingData<Data>>{
        return searchRepository.getSearchResult(query)
    }
}