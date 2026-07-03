package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.model.Search
import retrofit2.HttpException
import java.io.IOException

class SearchSource(private val moviesApi: MoviesApi, private val query: String): PagingSource<Int, Search>()  {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            val searchMovies = moviesApi.multiSearch(query = query, page = nextPage)
            LoadResult.Page(
                data = searchMovies.searches,
                prevKey = if (nextPage == 1) null else nextPage -1,
                nextKey = if (searchMovies.searches.isEmpty()) null else searchMovies.page +1
            )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }
}