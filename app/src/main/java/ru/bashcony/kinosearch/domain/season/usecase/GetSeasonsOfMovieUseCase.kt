package ru.bashcony.kinosearch.domain.season.usecase

import ru.bashcony.kinosearch.domain.movie.MovieRepository
import ru.bashcony.kinosearch.domain.season.SeasonRepository
import javax.inject.Inject

class GetSeasonsOfMovieUseCase @Inject constructor(
    private val seasonRepository: SeasonRepository
) {
    operator fun invoke(movieId: Int, limit: Int, page: Int) =
        seasonRepository.getSeasonsOfMovie(movieId, limit, page)
}