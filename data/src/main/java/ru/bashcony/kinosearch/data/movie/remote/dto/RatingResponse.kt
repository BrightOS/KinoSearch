package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RatingResponse(
    @SerializedName("kp") val kp: Double? = null,
    @SerializedName("imdb") val imdb: Double? = null,
    @SerializedName("tmdb") val tmdb: Double? = null,
    @SerializedName("filmCritics") val filmCritics: Double? = null,
    @SerializedName("russianFilmCritics") val russianFilmCritics: Double? = null,
    @SerializedName("await") val await: Double? = null
)
