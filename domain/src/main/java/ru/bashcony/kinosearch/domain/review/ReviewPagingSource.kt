package ru.bashcony.kinosearch.domain.review

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.review.entity.ReviewEntity
import ru.bashcony.kinosearch.domain.review.entity.ReviewsEntity
import ru.bashcony.kinosearch.domain.review.usecase.GetReviewsByMovieUseCase
import javax.inject.Inject

class ReviewPagingSource @Inject constructor(
    private val getReviewsByMovieUseCase: GetReviewsByMovieUseCase
) : RxPagingSource<Int, ReviewEntity>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ReviewEntity>> =
        getReviewsByMovieUseCase(movieId, 10, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position = params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }

    private fun toLoadResult(data: ReviewsEntity?, position: Int): LoadResult<Int, ReviewEntity> =
        LoadResult.Page(
            data = data?.docs.orEmpty(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < data?.pages!!) position + 1 else null
        )

    override fun getRefreshKey(state: PagingState<Int, ReviewEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}