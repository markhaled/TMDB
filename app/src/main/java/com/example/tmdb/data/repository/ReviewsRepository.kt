package com.example.tmdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Review
import com.example.tmdb.paging.ReviewsSource
import com.example.tmdb.util.MediaType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewsRepository @Inject constructor(private val moviesApi: MoviesApi) {
    fun getMediaReviews(movieId: Int, mediaType: MediaType): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false,pageSize = 20),
            pagingSourceFactory = {
                ReviewsSource(moviesApi = moviesApi, movieId = movieId, mediaType = mediaType)
            }
        ).flow
    }
}