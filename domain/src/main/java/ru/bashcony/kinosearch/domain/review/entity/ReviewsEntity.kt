package ru.bashcony.kinosearch.domain.review.entity

import androidx.annotation.Keep

@Keep
data class ReviewsEntity(
    val docs: List<ReviewEntity>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)