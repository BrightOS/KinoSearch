package ru.bashcony.kinosearch.domain.movie.usecase

import ru.bashcony.kinosearch.domain.movie.MovieRepository
import ru.bashcony.kinosearch.domain.movie.entity.SearchFilterEntity
import javax.inject.Inject

class FilteredSearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(filters: Map<SearchFilterEntity, Array<String>?>, limit: Int, page: Int) =
        movieRepository.filteredMovieSearch(
            filters.getOrDefault(SearchFilterEntity.YEAR, null),
            filters.getOrDefault(SearchFilterEntity.GENRES, null),
            filters.getOrDefault(SearchFilterEntity.COUNTRIES, null),
            filters.getOrDefault(SearchFilterEntity.AGE, null),
            limit,
            page
        )
}