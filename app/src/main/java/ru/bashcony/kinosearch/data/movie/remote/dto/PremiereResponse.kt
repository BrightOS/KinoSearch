package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class PremiereResponse(
    @SerializedName("world") val world: String?,
    @SerializedName("russia") val russia: String?,
    @SerializedName("digital") val digital: String?,
    @SerializedName("cinema") val cinema: String?
)
