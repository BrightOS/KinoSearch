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
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Runnable
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import ru.bashcony.kinosearch.domain.movie.paging.MovieSearchInvalidatingPagingSourceFactory
import ru.bashcony.kinosearch.domain.movie.usecase.GetAllMoviesFromHistoryDbUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.GetCountriesUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.GetGenresUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.InsertMovieToHistoryDbUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.RemoveMovieFromHistoryDbUseCase
import ru.bashcony.kinosearch.infra.utils.combineWith
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pagingSourceFactory: MovieSearchInvalidatingPagingSourceFactory,
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getAllMoviesFromHistoryDbUseCase: GetAllMoviesFromHistoryDbUseCase,
    private val insertMovieToHistoryDbUseCase: InsertMovieToHistoryDbUseCase,
    private val removeMovieFromHistoryDbUseCase: RemoveMovieFromHistoryDbUseCase,
    private val removeAllMoviesFromHistoryDbUseCase: GetAllMoviesFromHistoryDbUseCase
) : ViewModel() {

    init {
        println("SearchViewModel")
    }

    // Booleans:

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _showFilters: MutableLiveData<Boolean> = MutableLiveData(false)
    val showFilters: LiveData<Boolean>
        get() = _showFilters

    // Synchronized values for filters:

    private val _loadedGenres: MutableLiveData<List<ValueEntity>> = MutableLiveData(listOf())
    val loadedGenres: LiveData<List<ValueEntity>>
        get() = _loadedGenres

    private val _loadedCountries: MutableLiveData<List<ValueEntity>> = MutableLiveData(listOf())
    val loadedCountries: LiveData<List<ValueEntity>>
        get() = _loadedCountries

    // Selected filters:

    private val _selectedYears: MutableLiveData<Pair<Int, Int>> = MutableLiveData(Pair(-1, -1))
    val selectedYears: LiveData<Pair<Int, Int>>
        get() = _selectedYears

    private val _selectedAge: MutableLiveData<String> = MutableLiveData("")
    val selectedAge: LiveData<String>
        get() = _selectedAge

    private val _selectedGenres: MutableLiveData<List<ValueEntity>> = MutableLiveData(listOf())
    val selectedGenres: LiveData<List<ValueEntity>>
        get() = _selectedGenres

    private val _selectedCountries: MutableLiveData<List<ValueEntity>> = MutableLiveData(listOf())
    val selectedCountries: LiveData<List<ValueEntity>>
        get() = _selectedCountries

    // Search history:
    val searchHistory: LiveData<List<MovieEntity>>
        get() = getAllMoviesFromHistoryDbUseCase()


    val filteredSearchEnabled = selectedYears.combineWith(selectedGenres) { years, genres ->
        years?.first != -1 || genres.isNullOrEmpty().not()
    }.combineWith(selectedCountries) { yearsAndGenres, countries ->
        yearsAndGenres == true || countries.isNullOrEmpty().not()
    }.combineWith(selectedAge) { yearsAndGenres, countries ->
        yearsAndGenres == true || countries.isNullOrEmpty().not()
    }


    private val compositeDisposable = CompositeDisposable()
    private var moviesSubject = BehaviorSubject.create<PagingData<MovieEntity>>()
    private var searchSubject = PublishSubject.create<String>()


    fun switchFiltersVisibility() {
        println()
        _showFilters.postValue(_showFilters.value?.not())
    }


    fun selectGenres(genres: List<ValueEntity>) {
        _selectedGenres.postValue(genres)
        pagingSourceFactory.selectGenres(genres)
    }

    fun selectCountries(countries: List<ValueEntity>) {
        _selectedCountries.postValue(countries)
        pagingSourceFactory.selectCountries(countries)
    }

    fun selectYears(from: Int, to: Int) {
        _selectedYears.postValue(Pair(from, to))
        pagingSourceFactory.selectYears(from, to)
    }

    fun selectAge(age: String) {
        _selectedAge.postValue(age)
        pagingSourceFactory.selectAge(age)
    }


    fun cleanGenres() {
        _selectedGenres.postValue(listOf())
        pagingSourceFactory.cleanGenres()
    }

    fun cleanCountries() {
        _selectedCountries.postValue(listOf())
        pagingSourceFactory.cleanCountries()
    }

    fun cleanYears() {
        _selectedYears.postValue(Pair(-1, -1))
        pagingSourceFactory.cleanYears()
    }

    fun cleanAge() {
        _selectedAge.postValue("")
        pagingSourceFactory.cleanAge()
    }

    fun cleanFilters() {
        _selectedYears.postValue(Pair(-1, -1))
        _selectedGenres.postValue(listOf())
        _selectedCountries.postValue(listOf())
        _selectedAge.postValue("")
        pagingSourceFactory.cleanFilters()
    }


    fun setupEverything() {
        setupSearchMoviesPager()
        setupSearchSubject()
        loadGenres()
        loadCountries()
    }

    private fun setupSearchSubject() {
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
                    pagingSourceFactory.setMovieQuery(it)
                }, onError = {
                    it.printStackTrace()
                }
            )
            .addTo(compositeDisposable)
    }

    fun doOnMoviesLoaded(data: (PagingData<MovieEntity>) -> Unit) {
        moviesSubject
            .subscribe(data)
            .addTo(compositeDisposable)
    }

    private fun setupSearchMoviesPager() {
        Pager(
            config = PagingConfig(
                pageSize = 21,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory()
        )
            .observable
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moviesSubject)
    }

    private fun loadGenres() {
        getGenresUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                _loadedGenres.postValue(it)
            }, onError = {

            })
            .addTo(compositeDisposable)
    }

    private fun loadCountries() {
        getCountriesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                _loadedCountries.postValue(it)
            }, onError = {

            })
            .addTo(compositeDisposable)
    }


    fun forceSubmitQuery(movieQuery: String) {
        _loading.postValue(true)
        pagingSourceFactory.setMovieQuery(movieQuery)
    }

    fun sumbitQuery(movieQuery: String) {
        searchSubject.onNext(movieQuery)
    }


    fun removeMovieFromHistoryDb(movieEntity: MovieEntity) {
        Single.create {
            removeMovieFromHistoryDbUseCase(movieEntity)
            it.onSuccess(0)
        }
            .subscribeOn(Schedulers.io())
            .subscribeBy(onSuccess = {

            }, onError = {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun addMovieToHistoryDb(movieEntity: MovieEntity) {
        Single.create {
            println("ROOM SHIT SINGLE CREATED")
            val a = insertMovieToHistoryDbUseCase(movieEntity)
            println("ROOM SHIT SINGLE A")
            it.onSuccess(a)
        }
            .subscribeOn(Schedulers.io())
            .subscribeBy(onSuccess = {
                println("ROOM SHIT $it")
            }, onError = {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun stopLoading() {
        _loading.postValue(false)
    }
}