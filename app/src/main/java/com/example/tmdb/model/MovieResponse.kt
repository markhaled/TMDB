package com.example.tmdb.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)