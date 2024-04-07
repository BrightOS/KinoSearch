package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class TrailersResponse(
    @SerializedName("trailers") val trailers: List<VideoResponse>
)