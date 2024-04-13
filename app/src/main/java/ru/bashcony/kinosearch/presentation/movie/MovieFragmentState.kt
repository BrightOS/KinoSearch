package ru.bashcony.kinosearch.presentation.movie

import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity

sealed class MovieFragmentState {
    object Init : MovieFragmentState()
    data class IsLoading(val isLoading: Boolean) : MovieFragmentState()
    data class ShowToast(val message: String) : MovieFragmentState()
    data class MovieLoaded(val movieEntity: MovieEntity) : MovieFragmentState()
    data class ResponseError(val code: Int, val message: String) : MovieFragmentState()
    data class Error(val error: Throwable) : MovieFragmentState() // Make it raw
}