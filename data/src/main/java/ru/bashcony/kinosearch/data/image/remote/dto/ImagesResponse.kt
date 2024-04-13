package ru.bashcony.kinosearch.data.image.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class ImagesResponse(
    @SerializedName("docs") val docs: List<ImageResponse>? = listOf(),
    @SerializedName("total") val total: Int? = null,
    @SerializedName("limit") val limit: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("pages") val pages: Int? = null
)