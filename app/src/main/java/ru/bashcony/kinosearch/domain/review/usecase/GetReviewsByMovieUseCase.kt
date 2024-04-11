package ru.bashcony.kinosearch.domain.review.usecase

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse
import ru.bashcony.kinosearch.domain.review.ReviewRepository
import javax.inject.Inject

class GetReviewsByMovieUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(movieId: Int, limit: Int, page: Int): Single<Response<ReviewsResponse>> =
        reviewRepository.getReviewsByMovie(movieId, limit, page)
}