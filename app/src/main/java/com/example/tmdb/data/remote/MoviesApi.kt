package com.example.tmdb.data.remote


import com.example.tmdb.BuildConfig
import com.example.tmdb.model.CastResponse
import com.example.tmdb.model.GenreResponse
import com.example.tmdb.model.MovieResponse
import com.example.tmdb.model.ReviewsResponse
import com.example.tmdb.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY,
        @Query("page") page: Int= 0
    ): MovieResponse
    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY,
        @Query("page") page: Int= 0
    ): MovieResponse
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): GenreResponse
    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): GenreResponse
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("tv/on_the_air")
    suspend fun getOnAirTvShows(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("tv/{series_id}/recommendations")
    suspend fun getRecommendedTvShows(
        @Path("series_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("tv/{series_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("series_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): MovieResponse
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): CastResponse
    @GET("tv/{series_id}/credits")
    suspend fun getTvShowCast(
        @Path("series_id") movieId: Int,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): CastResponse
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): ReviewsResponse
    @GET("tv/{series_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("series_id") movieId: Int,
        @Query("page") page: Int= 0,
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY
    ): ReviewsResponse
    @GET("search/multi")
    suspend fun multiSearch(
        @Query("api_key") apiKey: String= BuildConfig.YOUR_API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int= 0
    ): SearchResponse
}