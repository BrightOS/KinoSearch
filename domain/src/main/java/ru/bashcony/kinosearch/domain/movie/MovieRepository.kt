package ru.bashcony.kinosearch.domain.movie

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity

interface MovieRepository {
    fun getMovieById(id: Int): Single<MovieEntity>
    fun movieSearch(
        query: String,
        limit: Int,
        page: Int
    ): Single<MoviesEntity>
}