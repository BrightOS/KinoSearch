package ru.bashcony.kinosearch.domain.movie.usecase

import ru.bashcony.kinosearch.domain.movie.MovieLocalRepository
import javax.inject.Inject

class RemoveAllFromHistoryDbUseCase @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
) {
    operator fun invoke() {
        movieLocalRepository.removeAllFromDb()
    }
}