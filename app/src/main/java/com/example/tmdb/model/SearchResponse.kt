package com.example.tmdb.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class SearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searches: List<Search>,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)