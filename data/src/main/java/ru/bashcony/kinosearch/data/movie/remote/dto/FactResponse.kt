package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FactResponse(
    @SerializedName("value") val value: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("spoiler") val spoiler: Boolean?
)
