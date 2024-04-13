package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class LinkedMovieEntity(
    val id: Int?,
    val name: String?,
    val enName: String?,
    val alternativeName: String?,
    val type: String?,
    val poster: MovieImageEntity?
)