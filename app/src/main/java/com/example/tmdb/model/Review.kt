package com.example.tmdb.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Review(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)