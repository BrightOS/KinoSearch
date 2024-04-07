package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class VotesResponse(
    @SerializedName("kp") val kp: Int?,
    @SerializedName("imdb") val imdb: Int?,
    @SerializedName("filmCritics") val filmCritics: Int?,
    @SerializedName("russianFilmCritics") val russianFilmCritics: Int?,
    @SerializedName("await") val await: Int?
)
