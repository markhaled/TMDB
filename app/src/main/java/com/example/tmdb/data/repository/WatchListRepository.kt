package com.example.tmdb.data.repository

import com.example.tmdb.data.local.WatchList
import com.example.tmdb.data.local.WatchListDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListRepository @Inject constructor(private val dataBase: WatchListDataBase) {
    suspend fun addToWatchList(watchList: WatchList){
        dataBase.watchListDao.addToWatchList(watchList)
    }
    suspend fun exists(mediaId: Int): Int{
        return dataBase.watchListDao.exists(mediaId)
    }
    suspend fun removeFromWatchList(mediaId: Int){
        dataBase.watchListDao.removeFromWatchList(mediaId)
    }
    fun getWatchList(): Flow<List<WatchList>> {
        return dataBase.watchListDao.getWatchList()
    }
    suspend fun deleteWatchList(){
        dataBase.watchListDao.deleteWatchList()
    }
}