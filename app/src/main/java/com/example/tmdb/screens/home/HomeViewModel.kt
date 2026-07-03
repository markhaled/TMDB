package com.example.tmdb.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.tmdb.data.repository.GenreRepository
import com.example.tmdb.data.repository.MovieRepository
import com.example.tmdb.model.Genre
import com.example.tmdb.model.Movie
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val genreRepository: GenreRepository):
    ViewModel() {
        var selectedGenre: MutableState<Genre> = mutableStateOf(Genre(null, "All"))
        var selectedMediaType: MutableState<MediaType> = mutableStateOf(MediaType.MOVIE)
        var randomMovie: Int? = null
        private var _movieGenres = mutableStateListOf(Genre(null, "All"))
        val movieGenres: SnapshotStateList<Genre> = _movieGenres
        private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val trendingMovies: State<Flow<PagingData<Movie>>> = _trendingMovies
        private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies
        private var _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val popularMovies: State<Flow<PagingData<Movie>>> = _popularMovies
        private var _upComingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val upComingMovies: State<Flow<PagingData<Movie>>> = _upComingMovies
        private var _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val nowPlayingMovies: State<Flow<PagingData<Movie>>> = _nowPlayingMovies
        private var _recommendedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
        val recommendedMovies: State<Flow<PagingData<Movie>>> = _recommendedMovies
    init {
        refresh()
    }
    fun getGenres(mediaType: MediaType = selectedMediaType.value){
        viewModelScope.launch {
            val genre = Genre(null,"All")
            when(val result = genreRepository.getMovieGenres(mediaType)){
                is Resource.Success ->{
                    _movieGenres.clear()
                    _movieGenres.add(genre)
                    selectedGenre.value = genre
                    result.data?.genres?.forEach {
                        _movieGenres.add(it)
                    }
                }
                is Resource.Error ->{
                    Log.d("tag","Error")
                }

                else -> {}
            }
        }
    }
    private fun getTrendingMovies(genreId: Int?,mediaType: MediaType){
        viewModelScope.launch {
            _trendingMovies.value= if (genreId != null ){
                movieRepository.getTrendingMovies(mediaType).map { results->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }

                }.cachedIn(viewModelScope)
            }else movieRepository.getTrendingMovies(mediaType).cachedIn(viewModelScope)
        }
    }
    private fun getTopRatedMovies(genreId: Int?,mediaType: MediaType){
        viewModelScope.launch {
            _topRatedMovies.value= if (genreId != null) movieRepository.getTopRatedMovies(mediaType).map { results->
                results.filter { movie ->
                    movie.genreIds!!.contains(genreId)
                }
            }.cachedIn(viewModelScope)
            else movieRepository.getTopRatedMovies(mediaType).cachedIn(viewModelScope)
        }
    }
    private fun getPopularMovies(genreId: Int?,mediaType: MediaType){
        viewModelScope.launch {
            _popularMovies.value= if (genreId != null) movieRepository.getPopularMovies(mediaType).map { results ->
                results.filter { movie ->
                    movie.genreIds!!.contains(genreId)
                }
            }.cachedIn(viewModelScope)
            else movieRepository.getPopularMovies(mediaType).cachedIn(viewModelScope)
        }
    }
    private fun getNowPlayingMovies(genreId: Int?,mediaType: MediaType){
        viewModelScope.launch {
            _nowPlayingMovies.value= if (genreId != null) movieRepository.getNowPlayingMovies(mediaType).map { results ->
                results.filter { movie ->
                    movie.genreIds!!.contains(genreId)
                }
            }.cachedIn(viewModelScope)
            else movieRepository.getNowPlayingMovies(mediaType).cachedIn(viewModelScope)
        }
    }
    private fun getUpcomingMovies(genreId: Int?) {
        viewModelScope.launch {
            _upComingMovies.value = if (genreId != null) movieRepository.getUpcomingMovies().map { results ->
                results.filter { movie ->
                    movie.genreIds!!.contains(genreId)
                }
            }.cachedIn(viewModelScope)
            else movieRepository.getUpcomingMovies().cachedIn(viewModelScope)
        }
    }
    fun getRecommendedMovies(movieId: Int,genreId: Int? = null,mediaType: MediaType = selectedMediaType.value){
        viewModelScope.launch {
            _recommendedMovies.value =
                if (genreId != null) movieRepository.getRecommendedMovies(movieId,mediaType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            else movieRepository.getRecommendedMovies(movieId,mediaType).cachedIn(viewModelScope)
        }
    }
    fun refresh(genreId: Int? = selectedGenre.value.id,mediaType: MediaType=selectedMediaType.value){
        if (movieGenres.size == 1){
            getGenres(selectedMediaType.value)
        }
        if (genreId == null){
            selectedGenre.value = Genre(null,"All")
        }
        getTrendingMovies(genreId,mediaType)
        getPopularMovies(genreId,mediaType)
        getTopRatedMovies(genreId,mediaType)
        getNowPlayingMovies(genreId,mediaType)
        getUpcomingMovies(genreId)
        randomMovie?.let { n -> getRecommendedMovies(n,genreId,mediaType) }
    }
    fun filterByGenre(genre: Genre){
        selectedGenre.value = genre
        refresh(genre.id)
    }
}