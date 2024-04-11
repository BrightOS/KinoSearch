package ru.bashcony.kinosearch.data.season.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse

interface SeasonApi {
    @GET("season")
    fun getSeasonsByMovie(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<Response<SeasonsResponse>>
}