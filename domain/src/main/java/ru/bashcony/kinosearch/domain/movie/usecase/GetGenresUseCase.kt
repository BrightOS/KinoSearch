package ru.bashcony.kinosearch.domain.movie.usecase

import ru.bashcony.kinosearch.domain.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getGenreNames()
}