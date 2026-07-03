package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Movie
import com.example.tmdb.util.MediaType
import retrofit2.HttpException
import java.io.IOException

class TrendingPagingSource(private val moviesApi: MoviesApi,private val mediaType: MediaType): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val trending = if (mediaType == MediaType.MOVIE) moviesApi.getTrendingMovies(page = nextPage)
            else moviesApi.getTrendingTvShows(page = nextPage)
            LoadResult.Page(
                data = trending.movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trending.movies.isEmpty()) null else trending.page + 1
            )
        } catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}