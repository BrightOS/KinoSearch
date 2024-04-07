package ru.bashcony.kinosearch.domain.movie

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse

interface MovieRepository {
    fun getMovieById(id: Int): Single<Response<MovieResponse>>
}