package ru.bashcony.kinosearch.data.season.remote.api

import androidx.annotation.Keep
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse

@Keep
interface SeasonApi {
    @GET("season")
    fun getSeasonsByMovie(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<SeasonsResponse>
}