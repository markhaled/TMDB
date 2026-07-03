package com.example.tmdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Search
import com.example.tmdb.paging.SearchSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val moviesApi: MoviesApi) {
    fun multiSearch(query: String) : Flow<PagingData<Search>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchSource(moviesApi = moviesApi, query = query)
            }
        ).flow
    }
}