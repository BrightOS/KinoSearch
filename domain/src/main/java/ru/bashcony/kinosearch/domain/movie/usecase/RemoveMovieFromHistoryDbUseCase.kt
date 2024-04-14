package ru.bashcony.kinosearch.domain.movie.usecase

import ru.bashcony.kinosearch.domain.movie.MovieLocalRepository
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import javax.inject.Inject

class RemoveMovieFromHistoryDbUseCase @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
) {
    operator fun invoke(movieEntity: MovieEntity) =
        movieLocalRepository.removeMovie(movieEntity)
}