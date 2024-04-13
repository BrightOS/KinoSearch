package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class MoviesEntity(
    val docs: List<MovieEntity>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)