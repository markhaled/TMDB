package com.example.tmdb.di

import android.app.Application
import androidx.room.Room
import com.example.tmdb.data.local.WatchListDataBase
import com.example.tmdb.data.remote.MoviesApi
import com.example.tmdb.data.repository.GenreRepository
import com.example.tmdb.data.repository.MovieRepository
import com.example.tmdb.data.repository.ReviewsRepository
import com.example.tmdb.data.repository.SearchRepository
import com.example.tmdb.data.repository.WatchListRepository
import com.example.tmdb.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesMoviesApi(): MoviesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

            .build()
            .create(MoviesApi::class.java)
    }
    @Singleton
    @Provides
    fun providesMovieRepository(moviesApi: MoviesApi) = MovieRepository(moviesApi)
    @Singleton
    @Provides
    fun providesGenreRepository(moviesApi: MoviesApi) = GenreRepository(moviesApi)
    @Singleton
    @Provides
    fun provideSearchRepository(moviesApi: MoviesApi) = SearchRepository(moviesApi)
    @Singleton
    @Provides
    fun providesReviewsRepository(moviesApi: MoviesApi) = ReviewsRepository(moviesApi)
    @Singleton
    @Provides
    fun providesWatchListRepository(db: WatchListDataBase) = WatchListRepository(db)
    @Singleton
    @Provides
    fun providesWatchListDatabase(app: Application): WatchListDataBase {
        return Room.databaseBuilder(
            app, WatchListDataBase::class.java, "watchlist_db"
        ).build()
    }

}