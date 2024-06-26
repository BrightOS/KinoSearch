package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VideoResponse(
    @SerializedName("url") val url: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("site") val site: String? = null,
    @SerializedName("size") val size: Long? = null,
    @SerializedName("type") val type: String? = null,
)
