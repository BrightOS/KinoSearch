package ru.bashcony.kinosearch.domain.person

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.person.remote.dto.PersonsResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse
import ru.bashcony.kinosearch.domain.person.usecase.GetPersonsByMovieUseCase
import ru.bashcony.kinosearch.domain.season.usecase.GetSeasonsOfMovieUseCase
import javax.inject.Inject

class PersonPagingSource @Inject constructor(
    private val getPersonsByMovieUseCase: GetPersonsByMovieUseCase
) : RxPagingSource<Int, PersonResponse>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PersonResponse>> =
        getPersonsByMovieUseCase(movieId, 30, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map {
                it.errorBody()?.let {
                    return@map LoadResult.Error<Int, PersonResponse>(Exception(it.string()))
                }

                val body = it.body() ?: PersonsResponse()

                val pageNumber = params.key ?: 1
                val previousKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey = if (pageNumber < body.pages!!) pageNumber + 1 else null

                return@map LoadResult.Page(
                    data = body.docs ?: listOf(),
                    prevKey = previousKey,
                    nextKey = nextKey
                )
            }

    override fun getRefreshKey(state: PagingState<Int, PersonResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}