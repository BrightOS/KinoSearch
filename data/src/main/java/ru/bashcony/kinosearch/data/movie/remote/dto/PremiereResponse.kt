package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PremiereResponse(
    @SerializedName("world") val world: String? = null,
    @SerializedName("russia") val russia: String? = null,
    @SerializedName("digital") val digital: String? = null,
    @SerializedName("cinema") val cinema: String? = null
)
