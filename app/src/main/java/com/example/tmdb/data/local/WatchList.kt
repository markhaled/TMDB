package com.example.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_list_table")
data class WatchList(
    @PrimaryKey
    val mediaId: Int,
    val imagePather: String,
    val releaseDate: String,
    val rating: Double,
    val addedOn: String
)
