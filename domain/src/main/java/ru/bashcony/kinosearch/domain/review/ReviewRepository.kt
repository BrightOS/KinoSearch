package ru.bashcony.kinosearch.domain.review

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.review.entity.ReviewsEntity

interface ReviewRepository {
    fun getReviewsByMovie(movieId: Int, limit: Int, page: Int): Single<ReviewsEntity>
}