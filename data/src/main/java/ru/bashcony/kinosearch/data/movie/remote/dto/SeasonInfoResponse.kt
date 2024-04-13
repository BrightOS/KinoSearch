package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeasonInfoResponse(
    @SerializedName("number") val number: Int? = null,
    @SerializedName("episodesCount") val episodesCount: Int? = null,
)