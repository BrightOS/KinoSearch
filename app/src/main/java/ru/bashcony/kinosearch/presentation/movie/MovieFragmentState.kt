package ru.bashcony.kinosearch.presentation.movie

import okhttp3.ResponseBody
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse

sealed class MovieFragmentState {
    object Init : MovieFragmentState()
    data class IsLoading(val isLoading: Boolean) : MovieFragmentState()
    data class ShowToast(val message: String) : MovieFragmentState()
    data class MovieLoaded(val movieResponse: MovieResponse) : MovieFragmentState()
    data class SeasonsLoaded(val seasonsResponse: SeasonsResponse) : MovieFragmentState()
    data class ResponseError(val code: Int, val message: String) : MovieFragmentState()
    data class Error(val error: Throwable) : MovieFragmentState() // Make it raw
}