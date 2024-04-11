package ru.bashcony.kinosearch.domain.review

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewResponse
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewsResponse
import ru.bashcony.kinosearch.domain.review.usecase.GetReviewsByMovieUseCase
import javax.inject.Inject

class ReviewPagingSource @Inject constructor(
    private val getReviewsByMovieUseCase: GetReviewsByMovieUseCase
) : RxPagingSource<Int, ReviewResponse>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ReviewResponse>> =
        getReviewsByMovieUseCase(movieId, 10, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map {
                it.errorBody()?.let {
                    return@map LoadResult.Error<Int, ReviewResponse>(Exception(it.string()))
                }

                val body = it.body() ?: ReviewsResponse()

                val pageNumber = params.key ?: 1
                val previousKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey = if (pageNumber < body.pages!!) pageNumber + 1 else null

                return@map LoadResult.Page(
                    data = body.docs ?: listOf(),
                    prevKey = previousKey,
                    nextKey = nextKey
                )
            }

    override fun getRefreshKey(state: PagingState<Int, ReviewResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}