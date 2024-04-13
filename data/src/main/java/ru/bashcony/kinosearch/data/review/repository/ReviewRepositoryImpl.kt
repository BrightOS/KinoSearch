package ru.bashcony.kinosearch.data.review.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.review.ReviewsMapper.toEntity
import ru.bashcony.kinosearch.data.review.remote.api.ReviewApi
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse
import ru.bashcony.kinosearch.domain.review.ReviewRepository
import ru.bashcony.kinosearch.domain.review.entity.ReviewsEntity
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewApi: ReviewApi
) : ReviewRepository {
    override fun getReviewsByMovie(
        movieId: Int,
        limit: Int,
        page: Int
    ): Single<ReviewsEntity> =
        reviewApi.getReviewsOfMovie(movieId = movieId, limit = limit, page = page)
            .map { it.toEntity() }
}