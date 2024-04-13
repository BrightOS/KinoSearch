package ru.bashcony.kinosearch.domain.person

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.person.entity.PersonEntity
import ru.bashcony.kinosearch.domain.person.entity.PersonsEntity
import ru.bashcony.kinosearch.domain.person.usecase.GetPersonsByMovieUseCase
import javax.inject.Inject

class PersonPagingSource @Inject constructor(
    private val getPersonsByMovieUseCase: GetPersonsByMovieUseCase
) : RxPagingSource<Int, PersonEntity>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PersonEntity>> =
        getPersonsByMovieUseCase(movieId, 21, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position = params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }

    private fun toLoadResult(data: PersonsEntity?, position: Int): LoadResult<Int, PersonEntity> =
        LoadResult.Page(
            data = data?.docs.orEmpty(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < data?.pages!!) position + 1 else null
        )

    override fun getRefreshKey(state: PagingState<Int, PersonEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}