package com.example.tmdb.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("cast")
    val cast: List<Cast?>? = null,
    @SerialName("crew")
    val crew: List<Crew?>? = null,
    @SerialName("id")
    val id: Int? = null
)