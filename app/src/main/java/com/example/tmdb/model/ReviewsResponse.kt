package com.example.tmdb.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class ReviewsResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("page")
    val page: Int ,
    @SerializedName("results")
    val reviews: List<Review>,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)