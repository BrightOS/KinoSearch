package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VotesResponse(
    @SerializedName("kp") val kp: Int?  = null,
    @SerializedName("imdb") val imdb: Int? = null,
    @SerializedName("filmCritics") val filmCritics: Int? = null,
    @SerializedName("russianFilmCritics") val russianFilmCritics: Int? = null,
    @SerializedName("await") val await: Int? = null
)
