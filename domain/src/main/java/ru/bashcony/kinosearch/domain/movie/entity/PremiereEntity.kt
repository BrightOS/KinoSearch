package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class PremiereEntity(
    val world: String?,
    val russia: String?,
    val digital: String?,
    val cinema: String?
)
