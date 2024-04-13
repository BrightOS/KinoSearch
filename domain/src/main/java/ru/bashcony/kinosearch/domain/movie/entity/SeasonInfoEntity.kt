package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class SeasonInfoEntity(
    val number: Int?,
    val episodesCount: Int?,
)