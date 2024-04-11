package ru.bashcony.kinosearch.domain.review

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse

interface ReviewRepository {
    fun getReviewsByMovie(movieId: Int, limit: Int, page: Int): Single<Response<ReviewsResponse>>
}