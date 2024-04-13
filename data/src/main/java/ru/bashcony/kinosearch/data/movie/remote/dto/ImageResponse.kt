package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImageResponse(
    @SerializedName("url") val url: String? = null,
    @SerializedName("previewUrl") val previewUrl: String? = null
)