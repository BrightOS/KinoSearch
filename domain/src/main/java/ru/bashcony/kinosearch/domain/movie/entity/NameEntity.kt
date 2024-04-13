package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class NameEntity(
    val name: String?,
    val language: String?,
    val type: String?
)
