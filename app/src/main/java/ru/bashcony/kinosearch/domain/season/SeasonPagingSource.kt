package ru.bashcony.kinosearch.domain.season

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse
import ru.bashcony.kinosearch.domain.season.usecase.GetSeasonsOfMovieUseCase
import javax.inject.Inject

class SeasonPagingSource @Inject constructor(
    private val getSeasonsOfMovieUseCase: GetSeasonsOfMovieUseCase
) : RxPagingSource<Int, SeasonResponse>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SeasonResponse>> =
        getSeasonsOfMovieUseCase(movieId, 10, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map {
                it.errorBody()?.let {
                    return@map LoadResult.Error<Int, SeasonResponse>(Exception(it.string()))
                }

                val body = it.body() ?: SeasonsResponse()

                val pageNumber = params.key ?: 1
                val previousKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey = if (pageNumber < body.pages!!) pageNumber + 1 else null

                return@map LoadResult.Page(
                    data = body.docs ?: listOf(),
                    prevKey = previousKey,
                    nextKey = nextKey
                )
            }

    override fun getRefreshKey(state: PagingState<Int, SeasonResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}