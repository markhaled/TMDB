package com.example.tmdb.data.repository

import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.GenreResponse
import com.example.tmdb.util.Resource
import com.example.tmdb.util.MediaType
import javax.inject.Inject

class GenreRepository @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getMovieGenres(mediaType: MediaType): Resource<GenreResponse> {
        val response = try {
            if (mediaType == MediaType.MOVIE ) moviesApi.getMovieGenres()
            else moviesApi.getTvShowGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        return Resource.Success(response)
    }
}