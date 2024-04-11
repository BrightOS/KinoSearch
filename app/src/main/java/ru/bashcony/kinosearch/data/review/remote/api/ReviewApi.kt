package ru.bashcony.kinosearch.data.review.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse

interface ReviewApi {
    @GET("review")
    fun getReviewsOfMovie(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<Response<ReviewsResponse>>
}