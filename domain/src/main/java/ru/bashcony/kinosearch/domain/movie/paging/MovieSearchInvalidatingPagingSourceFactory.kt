package ru.bashcony.kinosearch.domain.movie.paging

import androidx.paging.InvalidatingPagingSourceFactory
import ru.bashcony.kinosearch.domain.movie.usecase.SearchMovieUseCase
import javax.inject.Inject

class MovieSearchInvalidatingPagingSourceFactory @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
) {

    private var movieQuery: String? = null

    private val invalidatingFactory = InvalidatingPagingSourceFactory({
        MovieSearchPagingSource(searchMovieUseCase, movieQuery.toString())
    })

    fun setMovieQuery(movieQuery: String) {
        this.movieQuery = movieQuery
        invalidatingFactory.invalidate()
    }

    operator fun invoke() = invalidatingFactory
}