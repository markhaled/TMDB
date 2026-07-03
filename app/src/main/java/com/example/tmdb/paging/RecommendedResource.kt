package com.example.tmdb.paging

import androidx.compose.runtime.State
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Movie
import com.example.tmdb.util.MediaType
import retrofit2.HttpException
import java.io.IOException

class RecommendedResource (private val moviesApi: MoviesApi,private val movieId: Int,private val mediaType: MediaType):
    PagingSource<Int, Movie>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val recommendedMovies = if (mediaType == MediaType.MOVIE) moviesApi.getRecommendedMovies(page = nextPage,movieId = movieId)
            else moviesApi.getRecommendedTvShows(page = nextPage, movieId = movieId)
            LoadResult.Page(
                data = recommendedMovies.movies,
                prevKey = if (nextPage == 1) null else nextPage -1,
                nextKey = if (recommendedMovies.movies.isEmpty()) null else recommendedMovies.page +1
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