package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("url") val url: String?,
    @SerializedName("previewUrl") val previewUrl: String?
)