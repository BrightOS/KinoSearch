package ru.bashcony.kinosearch.domain.review.usecase

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.review.entity.ReviewsEntity
import ru.bashcony.kinosearch.domain.review.ReviewRepository
import javax.inject.Inject

class GetReviewsByMovieUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(movieId: Int, limit: Int, page: Int): Single<ReviewsEntity> =
        reviewRepository.getReviewsByMovie(movieId, limit, page)
}