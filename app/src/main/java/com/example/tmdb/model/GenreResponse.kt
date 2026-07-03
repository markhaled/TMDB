package com.example.tmdb.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)