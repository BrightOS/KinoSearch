package ru.bashcony.kinosearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.paging.MovieSearchInvalidatingPagingSourceFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieSearchInvalidatingPagingSourceFactory: MovieSearchInvalidatingPagingSourceFactory
) : ViewModel() {

    init {
        println("SearchViewModel")
    }

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val compositeDisposable = CompositeDisposable()
    private var moviesSubject = BehaviorSubject.create<PagingData<MovieEntity>>()
    private var searchSubject = PublishSubject.create<String>()

    fun setupSearchSubject(startQuery: String? = null) {
        compositeDisposable.clear()

        searchSubject
            .map { query ->
                _loading.postValue(true)
                query.trim()
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    movieSearchInvalidatingPagingSourceFactory.setMovieQuery(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun doOnMoviesLoaded(data: (PagingData<MovieEntity>) -> Unit) {
        moviesSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    fun setupSearchMoviesPager() {
        Pager(
            config = PagingConfig(pageSize = 21),
            pagingSourceFactory = movieSearchInvalidatingPagingSourceFactory()
        )
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesSubject)
    }

    fun forceSubmitQuery(movieQuery: String) {
        _loading.postValue(true)
        movieSearchInvalidatingPagingSourceFactory.setMovieQuery(movieQuery)
    }

    fun sumbitQuery(movieQuery: String) {
        searchSubject.onNext(movieQuery)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun stopLoading() {
        _loading.postValue(false)
    }
}