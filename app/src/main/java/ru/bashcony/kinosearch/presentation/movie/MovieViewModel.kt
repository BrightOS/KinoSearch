package ru.bashcony.kinosearch.presentation.movie

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.domain.movie.usecase.GetMovieByIdUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<MovieFragmentState>()
    val state: LiveData<MovieFragmentState>
        get() = _state

    fun getMovieById(movieId: Int) {
        getMovieByIdUseCase(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.postValue(MovieFragmentState.IsLoading(isLoading = true))
            }
            .subscribeBy(
                onSuccess = {
                    when {
                        it.code() / 100 == 2 -> {
                            _state.postValue(it.body()
                                ?.let { MovieFragmentState.Success(it) })
                        }
                        it.code() / 100 > 3 -> {
                            _state.postValue(it.errorBody()
                                ?.let { MovieFragmentState.ErrorResponse(it) })
                        }
                    }
                },
                onError = {
                    _state.postValue(MovieFragmentState.Error(it))
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

}