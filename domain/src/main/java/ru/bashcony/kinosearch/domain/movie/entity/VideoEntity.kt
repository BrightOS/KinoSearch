package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class VideoEntity(
    val url: String?,
    val name: String?,
    val site: String?,
    val size: Long?,
    val type: String?,
)
