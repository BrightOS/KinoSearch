package ru.bashcony.kinosearch.domain.season.entity

import androidx.annotation.Keep

@Keep
data class SeasonsEntity(
    val docs: List<SeasonEntity>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)