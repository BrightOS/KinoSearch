package ru.bashcony.kinosearch.data.movie.remote.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse

interface MovieApi {

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id: Int): Single<Response<MovieResponse>>
}