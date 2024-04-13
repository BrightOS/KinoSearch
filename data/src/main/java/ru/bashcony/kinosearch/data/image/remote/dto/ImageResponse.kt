package ru.bashcony.kinosearch.data.image.remote.dto

import android.icu.text.ListFormatter.Width
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImageResponse(
    @SerializedName("movieId") val movieId: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("previewUrl") val previewUrl: String? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)
