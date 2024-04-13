package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class FactEntity(
    val value: String?,
    val type: String?,
    val spoiler: Boolean?
)
