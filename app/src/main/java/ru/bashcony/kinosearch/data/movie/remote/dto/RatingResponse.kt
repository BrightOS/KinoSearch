package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("kp") val kp: Double?,
    @SerializedName("imdb") val imdb: Double?,
    @SerializedName("tmdb") val tmdb: Double?,
    @SerializedName("filmCritics") val filmCritics: Double?,
    @SerializedName("russianFilmCritics") val russianFilmCritics: Double?,
    @SerializedName("await") val await: Double?
)
