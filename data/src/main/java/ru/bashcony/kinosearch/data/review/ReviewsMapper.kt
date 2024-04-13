package ru.bashcony.kinosearch.data.review

import ru.bashcony.kinosearch.data.review.remote.dto.ReviewResponse
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse
import ru.bashcony.kinosearch.domain.review.entity.ReviewEntity
import ru.bashcony.kinosearch.domain.review.entity.ReviewsEntity

object ReviewsMapper {
    fun ReviewResponse.toEntity() =
        ReviewEntity(
            id ?: -1,
            movieId ?: -1,
            title,
            type,
            review,
            date,
            author,
            userRating,
            authorId ?: -1,
            createdAt,
            updatedAt.orEmpty()
        )

    fun ReviewsResponse.toEntity() =
        ReviewsEntity(
            docs.orEmpty().map { it.toEntity() },
            total,
            limit,
            page,
            pages
        )
}