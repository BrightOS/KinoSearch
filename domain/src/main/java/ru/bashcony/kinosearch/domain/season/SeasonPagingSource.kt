package ru.bashcony.kinosearch.domain.season

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.season.entity.SeasonEntity
import ru.bashcony.kinosearch.domain.season.entity.SeasonsEntity
import ru.bashcony.kinosearch.domain.season.usecase.GetSeasonsOfMovieUseCase
import javax.inject.Inject

class SeasonPagingSource @Inject constructor(
    private val getSeasonsOfMovieUseCase: GetSeasonsOfMovieUseCase
) : RxPagingSource<Int, SeasonEntity>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SeasonEntity>> =
        getSeasonsOfMovieUseCase(movieId, 10, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position = params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }

    private fun toLoadResult(data: SeasonsEntity?, position: Int): LoadResult<Int, SeasonEntity> =
        LoadResult.Page(
            data = data?.docs.orEmpty(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < data?.pages!!) position + 1 else null
        )

    override fun getRefreshKey(state: PagingState<Int, SeasonEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}