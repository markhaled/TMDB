package com.example.tmdb.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Movie
import retrofit2.HttpException
import java.io.IOException

class UpcomingSource (private val moviesApi: MoviesApi): PagingSource<Int, Movie>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val upcomingMovies = moviesApi.getUpcomingMovies(page = nextPage)
            LoadResult.Page(
                data = upcomingMovies.movies,
                prevKey = if (nextPage == 1) null else nextPage-1,
                nextKey = if (upcomingMovies.movies.isEmpty()) null else upcomingMovies.page +1
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