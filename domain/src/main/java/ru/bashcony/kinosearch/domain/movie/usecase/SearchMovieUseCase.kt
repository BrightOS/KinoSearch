package ru.bashcony.kinosearch.domain.movie.usecase

import ru.bashcony.kinosearch.domain.movie.MovieRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String, limit: Int, page: Int) =
        movieRepository.movieSearch(query, limit, page)
}