package ru.bashcony.kinosearch.presentation.movie

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.domain.person.entity.PersonEntity
import ru.bashcony.kinosearch.domain.review.entity.ReviewEntity
import ru.bashcony.kinosearch.domain.season.entity.SeasonEntity
import ru.bashcony.kinosearch.domain.image.ImagePagingSource
import ru.bashcony.kinosearch.domain.movie.usecase.GetMovieByIdUseCase
import ru.bashcony.kinosearch.domain.person.PersonPagingSource
import ru.bashcony.kinosearch.domain.review.ReviewPagingSource
import ru.bashcony.kinosearch.domain.season.SeasonPagingSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val seasonPagingSource: SeasonPagingSource,
    private val personPagingSource: PersonPagingSource,
    private val reviewPagingSource: ReviewPagingSource,
    private val imagePagingSource: ImagePagingSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<MovieFragmentState>(MovieFragmentState.Init)
    val state: LiveData<MovieFragmentState>
        get() = _state

    private var seasonsSubject = BehaviorSubject.create<PagingData<SeasonEntity>>()
    private var personsSubject = BehaviorSubject.create<PagingData<PersonEntity>>()
    private var reviewsSubject = BehaviorSubject.create<PagingData<ReviewEntity>>()
    private var imagesSubject = BehaviorSubject.create<PagingData<ImageEntity>>()

    fun doOnSeasonsLoaded(data: (PagingData<SeasonEntity>) -> Unit) {
        seasonsSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    fun doOnPersonsLoaded(data: (PagingData<PersonEntity>) -> Unit) {
        personsSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    fun doOnReviewsLoaded(data: (PagingData<ReviewEntity>) -> Unit) {
        reviewsSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    fun doOnImagesLoaded(data: (PagingData<ImageEntity>) -> Unit) {
        imagesSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    fun loadMovieInformation(movieId: Int) {
        setupSeasonsPager(movieId)
        setupPersonsPager(movieId)
        setupReviewsPager(movieId)
        setupImagesPager(movieId)
        getMovieById(movieId)
    }

    private fun setupSeasonsPager(movieId: Int) {
        Pager(PagingConfig(pageSize = 10)) {
            seasonPagingSource.apply {
                setMovieId(movieId)
            }
        }
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(seasonsSubject)
    }

    private fun setupPersonsPager(movieId: Int) {
        Pager(PagingConfig(pageSize = 21)) {
            personPagingSource.apply {
                setMovieId(movieId)
            }
        }
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(personsSubject)
    }

    private fun setupReviewsPager(movieId: Int) {
        Pager(PagingConfig(pageSize = 10)) {
            reviewPagingSource.apply {
                setMovieId(movieId)
            }
        }
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(reviewsSubject)
    }

    private fun setupImagesPager(movieId: Int) {
        Pager(PagingConfig(pageSize = 10)) {
            imagePagingSource.apply {
                setMovieId(movieId)
            }
        }
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(imagesSubject)
    }

//    private fun getSeasonsByMovie(movieId: Int) {
//        getSeasonsOfMovieUseCase(movieId).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onSuccess = {
//                when {
//                    it.code() / 100 == 2 -> {
//                        _seasonsList.postValue(it.body())
//                    }
//
//                    it.code() / 100 in (3..4) -> {
//                        _seasonsList.postValue(SeasonsResponse())
//                    }
//
//                    else -> {
//                        _state.postValue(
//                            MovieFragmentState.ResponseError(
//                                it.code(), it.message()
//                            )
//                        )
//                    }
//                }
//            }, onError = {
//                _state.postValue(MovieFragmentState.Error(it))
//            }).addTo(compositeDisposable)
//    }

    private fun getMovieById(movieId: Int) {
        getMovieByIdUseCase(movieId)
            .delay(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.postValue(MovieFragmentState.IsLoading(isLoading = true))
            }.subscribeBy(onSuccess = {
                _state.postValue(MovieFragmentState.MovieLoaded(it))
            }, onError = {
                _state.postValue(MovieFragmentState.Error(it))
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

}