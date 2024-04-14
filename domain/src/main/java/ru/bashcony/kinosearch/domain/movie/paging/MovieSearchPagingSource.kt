package ru.bashcony.kinosearch.domain.movie.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import ru.bashcony.kinosearch.domain.movie.entity.SearchFilterEntity
import ru.bashcony.kinosearch.domain.movie.usecase.FilteredSearchMovieUseCase
import ru.bashcony.kinosearch.domain.movie.usecase.SearchMovieUseCase

class MovieSearchPagingSource constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val filteredSearchMovieUseCase: FilteredSearchMovieUseCase,
    private val movieQuery: String? = null,
    private val filters: Map<SearchFilterEntity, Array<String>?>? = null
) : RxPagingSource<Int, MovieEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieEntity>> =
        if (filters.isNullOrEmpty())
            searchMovieUseCase(movieQuery.orEmpty(), 21, params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position = params.key ?: 1) }
                .onErrorReturn { LoadResult.Error(it) }
        else
            filteredSearchMovieUseCase(filters, 21, params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position = params.key ?: 1) }
                .onErrorReturn { LoadResult.Error(it) }

    private fun toLoadResult(data: MoviesEntity?, position: Int): LoadResult<Int, MovieEntity> =
        LoadResult.Page(
            data = data?.docs.orEmpty(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < data?.pages!!) position + 1 else null
        )

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            println("Searching for refresh key... ${anchorPage?.prevKey} ${anchorPage?.nextKey}")
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}