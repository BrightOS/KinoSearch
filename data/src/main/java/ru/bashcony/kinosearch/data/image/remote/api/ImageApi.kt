package ru.bashcony.kinosearch.data.image.remote.api

import androidx.annotation.Keep
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.image.remote.dto.ImagesResponse

@Keep
interface ImageApi {
    @GET("image")
    fun getImagesByMovie(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<ImagesResponse>
}