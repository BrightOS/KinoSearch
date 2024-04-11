package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class SeasonInfoResponse(
    @SerializedName("number") val number: Int,
    @SerializedName("episodesCount") val episodesCount: Int,
)