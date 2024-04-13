package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class TrailersEntity(
    val trailers: List<VideoEntity>?
)