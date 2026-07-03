package com.example.tmdb.screens.watchList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.local.WatchList
import com.example.tmdb.data.repository.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val watchListRepository: WatchListRepository): ViewModel() {
    private val _watchList = mutableStateOf<Flow<List<WatchList>>>(emptyFlow())
    val watchList: MutableState<Flow<List<WatchList>>> = _watchList
    private val _addedToWatchList = mutableIntStateOf(0)
    val addedToWatchList: State<Int> = _addedToWatchList
    init {
        getWatchList()
    }
    private fun getWatchList(){
        _watchList.value = watchListRepository.getWatchList()
    }
    fun exists(mediaId: Int){
        viewModelScope.launch {
            _addedToWatchList.intValue = watchListRepository.exists(mediaId)
        }
    }
    fun addToWatchList(movie: WatchList){
        viewModelScope.launch {
            watchListRepository.addToWatchList(movie)
        }.invokeOnCompletion {
            exists(movie.mediaId)
        }
    }
    fun removeFromWatchList(mediaId: Int){
        viewModelScope.launch {
            watchListRepository.removeFromWatchList(mediaId)
        }.invokeOnCompletion {
            exists(mediaId)
        }
    }
    fun deleteWatchList(){
        viewModelScope.launch { watchListRepository.deleteWatchList() }
    }
}