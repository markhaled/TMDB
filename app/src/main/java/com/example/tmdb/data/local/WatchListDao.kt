package com.example.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(watchList: WatchList)
    @Query("DELETE FROM watch_list_table WHERE mediaId =:mediaId")
    suspend fun removeFromWatchList(mediaId: Int)
    @Query("DELETE FROM watch_list_table")
    suspend fun deleteWatchList()
    @Query("SELECT * FROM watch_list_table ORDER BY addedOn DESC")
    fun getWatchList(): Flow<List<WatchList>>
    @Query("SELECT EXISTS (SELECT 1 FROM watch_list_table WHERE mediaId =:mediaId)")
    suspend fun exists(mediaId: Int): Int
}
