package com.example.tmdb.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [WatchList::class], version = 1, exportSchema = false)
abstract class WatchListDataBase: RoomDatabase() {
    abstract val watchListDao: WatchListDao

}