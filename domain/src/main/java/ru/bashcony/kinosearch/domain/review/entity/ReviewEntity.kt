package ru.bashcony.kinosearch.domain.review.entity

import androidx.annotation.Keep

@Keep
data class ReviewEntity(
    val id: Int?,
    val movieId: Int?,
    val title: String?,
    val type: String?,
    val review: String?,
    val date: String?,
    val author: String?,
    val userRating: Double?,
    val authorId: Int?,
    val createdAt: String?,
    val updatedAt: String?
)
