package com.example.tmdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.CastResponse
import com.example.tmdb.model.Movie
import com.example.tmdb.paging.NowPlayingSource
import com.example.tmdb.paging.PopularSource
import com.example.tmdb.paging.RecommendedResource
import com.example.tmdb.paging.SimilarSource
import com.example.tmdb.paging.TopRatedSource
import com.example.tmdb.paging.TrendingPagingSource
import com.example.tmdb.paging.UpcomingSource
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val moviesApi: MoviesApi) {
    fun getTrendingMovies(mediaType: MediaType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                TrendingPagingSource(moviesApi = moviesApi,mediaType)
            }
        ).flow
    }
    fun getTopRatedMovies(mediaType: MediaType): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                TopRatedSource(moviesApi = moviesApi, mediaType)
            }
        ).flow
    }
    fun getPopularMovies(mediaType: MediaType): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                PopularSource(moviesApi = moviesApi, mediaType)
            }
        ).flow
    }
    fun getUpcomingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                UpcomingSource(moviesApi = moviesApi)
            }
        ).flow
    }
    fun getSimilarMovies(movieId: Int,mediaType: MediaType): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                SimilarSource(moviesApi = moviesApi, movieId, mediaType)
            }
        ).flow
    }
    fun getNowPlayingMovies(mediaType: MediaType): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                NowPlayingSource(moviesApi = moviesApi, mediaType = mediaType)
            }
        ).flow
    }
    fun getRecommendedMovies(movieId: Int,mediaType: MediaType): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                RecommendedResource(moviesApi = moviesApi, movieId, mediaType)
            }
        ).flow
    }
    suspend fun getMovieCast(movieId: Int,mediaType: MediaType): Resource<CastResponse>{
        val response = try {
            if (mediaType == MediaType.MOVIE) moviesApi.getMovieCast(movieId)
            else moviesApi.getTvShowCast(movieId)
        }catch (e: Exception){
            return Resource.Error("Unknown Error")
        }
        return Resource.Success(response)
    }
}