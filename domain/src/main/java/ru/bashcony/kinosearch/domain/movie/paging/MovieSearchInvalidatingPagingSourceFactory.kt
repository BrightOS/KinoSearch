package ru.bashcony.kinosearch.domain.movie.paging

import androidx.paging.InvalidatingPagingSourceFactory
import ru.bashcony.kinosearch.domain.movie.entity.SearchFilterEntity
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import ru.bashcony.kinosearch.domain.movie.usecase.FilteredSearchMovieUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.SearchMovieUseCase
import javax.inject.Inject

class MovieSearchInvalidatingPagingSourceFactory @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val filteredSearchMovieUseCase: FilteredSearchMovieUseCase
) {

    private var movieQuery: String? = null
    private var filters: Map<SearchFilterEntity, Array<String>?> = mapOf()

    private val invalidatingFactory = InvalidatingPagingSourceFactory {
        MovieSearchPagingSource(searchMovieUseCase, filteredSearchMovieUseCase, movieQuery, filters)
    }



    fun setMovieQuery(movieQuery: String) {
        this.movieQuery = movieQuery
        invalidatingFactory.invalidate()
    }



    fun selectCountries(countries: List<ValueEntity>) {
        filters = filters
            .minus(SearchFilterEntity.COUNTRIES)
            .plus(
                Pair(
                    SearchFilterEntity.COUNTRIES,
                    countries.map { it.name.orEmpty() }.toTypedArray()
                )
            )

        invalidatingFactory.invalidate()
    }

    fun selectGenres(genres: List<ValueEntity>) {
        filters = filters
            .minus(SearchFilterEntity.GENRES)
            .plus(
                Pair(
                    SearchFilterEntity.GENRES,
                    genres.map { it.name.orEmpty() }.toTypedArray()
                )
            )

        invalidatingFactory.invalidate()
    }

    fun selectYears(from: Int, to: Int) {
        filters = filters
            .minus(SearchFilterEntity.YEAR)
            .plus(Pair(SearchFilterEntity.YEAR, arrayOf("$from-$to")))

        invalidatingFactory.invalidate()
    }

    fun selectAge(age: String) {
        filters = filters
            .minus(SearchFilterEntity.AGE)
            .plus(Pair(SearchFilterEntity.AGE, arrayOf(age)))

        invalidatingFactory.invalidate()
    }



    fun cleanCountries() {
        filters = filters
            .minus(SearchFilterEntity.COUNTRIES)

        invalidatingFactory.invalidate()
    }

    fun cleanGenres() {
        filters = filters
            .minus(SearchFilterEntity.GENRES)

        invalidatingFactory.invalidate()
    }

    fun cleanYears() {
        filters = filters
            .minus(SearchFilterEntity.YEAR)

        invalidatingFactory.invalidate()
    }

    fun cleanAge() {
        filters = filters
            .minus(SearchFilterEntity.AGE)

        invalidatingFactory.invalidate()
    }

    fun cleanFilters() {
        filters = mapOf()
        invalidatingFactory.invalidate()
    }

    operator fun invoke() = invalidatingFactory

}