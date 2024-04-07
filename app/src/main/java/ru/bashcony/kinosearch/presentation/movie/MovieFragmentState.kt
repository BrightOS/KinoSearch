package ru.bashcony.kinosearch.presentation.movie

import okhttp3.ResponseBody
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse

sealed class MovieFragmentState {
    object Init : MovieFragmentState()
    data class IsLoading(val isLoading: Boolean) : MovieFragmentState()
    data class ShowToast(val message: String) : MovieFragmentState()
    data class Success(val movieResponse: MovieResponse) : MovieFragmentState()
    data class ErrorResponse(val errorResponse: ResponseBody) : MovieFragmentState()
    data class Error(val error: Throwable) : MovieFragmentState() // Make it raw
}