package ru.bashcony.kinosearch.domain.movie.usecase

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.domain.movie.MovieRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Single<Response<MovieResponse>> =
        movieRepository.getMovieById(movieId)
}