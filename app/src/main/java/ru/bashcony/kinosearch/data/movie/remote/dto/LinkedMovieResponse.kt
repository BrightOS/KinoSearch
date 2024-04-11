package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class LinkedMovieResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("enName") val enName: String? = null,
    @SerializedName("alternativeName") val alternativeName: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("poster") val poster: ImageResponse? = null,
)