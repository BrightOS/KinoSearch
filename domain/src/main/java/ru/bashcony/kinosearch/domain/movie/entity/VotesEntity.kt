package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class VotesEntity(
    val kp: Int?,
    val imdb: Int?,
    val filmCritics: Int?,
    val russianFilmCritics: Int?,
    val await: Int?
)
