package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Review
import com.example.tmdb.util.MediaType
import retrofit2.HttpException
import java.io.IOException

class ReviewsSource(private val moviesApi: MoviesApi, private val movieId: Int, private val mediaType: MediaType): PagingSource<Int, Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPage= params.key ?: 1
            val mediaReviews = if (mediaType == MediaType.MOVIE) moviesApi.getMovieReviews(movieId = movieId, page = nextPage)
            else moviesApi.getTvShowReviews(movieId = movieId, page = nextPage)
            LoadResult.Page(
                data = mediaReviews.reviews,
                prevKey = if (nextPage == 1) null else nextPage -1,
                nextKey = if (mediaReviews.reviews.isEmpty()) null else mediaReviews.page +1
            )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition
    }
}