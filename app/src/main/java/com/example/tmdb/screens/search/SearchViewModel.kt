package com.example.tmdb.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.tmdb.data.repository.SearchRepository
import com.example.tmdb.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository): ViewModel() {
    private var _multiSearch = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val multiSearch: State<Flow<PagingData<Search>>> = _multiSearch
    var searchQuery = mutableStateOf("")
    var searchQueryState: State<String> = searchQuery
    fun search(){
        viewModelScope.launch {
            if (searchQuery.value.isNotEmpty()){
                _multiSearch.value = searchRepository.multiSearch(searchQuery.value).map { results ->
                    results.filter {
                        ((it.title != null || it.originalTitle != null || it.originalName != null)&&(it.mediaType == "movie" || it.mediaType == "tv"))
                    }
                }.cachedIn(viewModelScope)
            }
        }
    }
}