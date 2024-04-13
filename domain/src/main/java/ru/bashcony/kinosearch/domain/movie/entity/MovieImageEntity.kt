package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class MovieImageEntity(
    val url: String?,
    val previewUrl: String?
)