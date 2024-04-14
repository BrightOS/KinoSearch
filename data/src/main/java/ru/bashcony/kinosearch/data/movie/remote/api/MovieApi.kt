package ru.bashcony.kinosearch.data.movie.remote.api

import androidx.annotation.Keep
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MoviesResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.ValueResponse

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

    @GET("movie")
    fun filteredMovieSearch(
        @Query("year") year: Array<String> = arrayOf(),
        @Query("genres.name") genres: Array<String> = arrayOf(),
        @Query("countries.name") countries: Array<String> = arrayOf(),
        @Query("ratingMpaa") age: Array<String> = arrayOf(),
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<MoviesResponse>

    @GET
    fun getCountryNames(
        @Url url: String = "https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=countries.name"
    ): Single<List<ValueResponse>>

    @GET
    fun getGenreNames(
        @Url url: String = "https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=genres.name"
    ): Single<List<ValueResponse>>
}