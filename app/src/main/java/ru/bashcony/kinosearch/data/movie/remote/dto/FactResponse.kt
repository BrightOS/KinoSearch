package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class FactResponse(
    @SerializedName("value") val value: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("spoiler") val spoiler: Boolean?
)
