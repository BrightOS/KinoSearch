package ru.bashcony.kinosearch.domain.movie.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import ru.bashcony.kinosearch.domain.movie.usecase.SearchMovieUseCase

class MovieSearchPagingSource constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val movieQuery: String
) : RxPagingSource<Int, MovieEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieEntity>> =
        searchMovieUseCase(movieQuery, 21, params.key ?: 1)
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
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}