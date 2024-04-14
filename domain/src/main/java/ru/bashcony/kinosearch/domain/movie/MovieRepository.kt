package ru.bashcony.kinosearch.domain.movie

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity

interface MovieRepository {
    fun getMovieById(id: Int): Single<MovieEntity>
    fun movieSearch(
        query: String,
        limit: Int,
        page: Int
    ): Single<MoviesEntity>

    fun filteredMovieSearch(
        year: Array<String>?,
        genres: Array<String>?,
        countries: Array<String>?,
        age: Array<String>?,
        limit: Int,
        page: Int
    ): Single<MoviesEntity>

    fun getGenreNames(): Single<List<ValueEntity>>
    fun getCountryNames(): Single<List<ValueEntity>>
}