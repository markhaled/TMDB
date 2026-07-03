package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Movie
import com.example.tmdb.util.MediaType
import retrofit2.HttpException
import java.io.IOException

class SimilarSource(private val moviesApi: MoviesApi, private val movieId: Int, private val mediaType: MediaType): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage= params.key ?: 1
            val similarMovies =
                if (mediaType == MediaType.MOVIE) moviesApi.getSimilarMovies(page = nextPage, movieId = movieId)
            else moviesApi.getSimilarTvShows(movieId = movieId, page = nextPage)
            LoadResult.Page(
                data = similarMovies.movies,
                prevKey = if (nextPage == 1) null else nextPage-1,
                nextKey = if (similarMovies.movies.isEmpty()) null else similarMovies.page +1
            )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}