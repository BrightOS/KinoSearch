package ru.bashcony.kinosearch.data.season.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeasonsResponse(
    @SerializedName("docs") val docs: List<SeasonResponse>? = listOf(),
    @SerializedName("total") val total: Int? = null,
    @SerializedName("limit") val limit: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("pages") val pages: Int? = null
)