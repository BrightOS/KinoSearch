package ru.bashcony.kinosearch.data.movie.remote.api

import androidx.annotation.Keep
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MoviesResponse

@Keep
interface MovieApi {

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id: Int
    ): Single<MovieResponse>

    @GET("movie/search")
    fun movieSearch(
        @Query("query") query: String = "",
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<MoviesResponse>

    @GET("movie/search")
    fun movieSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<MoviesResponse>
}