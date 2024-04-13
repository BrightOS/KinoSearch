package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TrailersResponse(
    @SerializedName("trailers") val trailers: List<VideoResponse>? = listOf()
)