package ru.bashcony.kinosearch.data.movie.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.movie.MoviesMapper.toEntity
import ru.bashcony.kinosearch.data.movie.remote.api.MovieApi
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MoviesResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.ValueResponse
import ru.bashcony.kinosearch.domain.movie.MovieRepository
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getMovieById(id: Int): Single<MovieEntity> =
        movieApi.getMovieById(id)
            .map { it.toEntity() }

    override fun movieSearch(
        query: String,
        limit: Int,
        page: Int
    ): Single<MoviesEntity> =
        movieApi.movieSearch(
            query.let {
                if (it == "null")
                    ""
                else
                    it
            },
            page,
            limit
        ).map { it.toEntity() }

    override fun filteredMovieSearch(
        year: Array<String>?,
        genres: Array<String>?,
        countries: Array<String>?,
        age: Array<String>?,
        limit: Int,
        page: Int
    ): Single<MoviesEntity> =
        movieApi.filteredMovieSearch(
            year = year ?: arrayOf(),
            genres = genres ?: arrayOf(),
            countries = countries ?: arrayOf(),
            age = age ?: arrayOf(),
            page = page,
            limit = limit
        ).map { it.toEntity() }

    override fun getCountryNames(): Single<List<ValueEntity>> =
        movieApi.getCountryNames().map { it.map { it.toEntity() } }

    override fun getGenreNames(): Single<List<ValueEntity>> =
        movieApi.getGenreNames().map { it.map { it.toEntity() } }

}