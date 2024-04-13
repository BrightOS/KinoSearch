package ru.bashcony.kinosearch.domain.movie.usecase

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.movie.MovieRepository
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Single<MovieEntity> =
        movieRepository.getMovieById(movieId)
}