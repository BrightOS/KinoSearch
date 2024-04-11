package ru.bashcony.kinosearch.data.movie.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.movie.remote.api.MovieApi
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.domain.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getMovieById(id: Int): Single<Response<MovieResponse>> =
        movieApi.getMovieById(id)

}