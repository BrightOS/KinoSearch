package ru.bashcony.kinosearch.domain.image

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.domain.image.entity.ImagesEntity
import ru.bashcony.kinosearch.domain.image.usecase.GetImagesByMovieUseCase
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import javax.inject.Inject

class ImagePagingSource @Inject constructor(
    private val getImagesByMovieUseCase: GetImagesByMovieUseCase
) : RxPagingSource<Int, ImageEntity>() {

    private var movieId: Int = 0

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ImageEntity>> =
        getImagesByMovieUseCase(movieId, 10, params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position = params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }

    private fun toLoadResult(data: ImagesEntity?, position: Int): LoadResult<Int, ImageEntity> =
        LoadResult.Page(
            data = data?.docs.orEmpty(),
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < data?.pages!!) position + 1 else null
        )

    override fun getRefreshKey(state: PagingState<Int, ImageEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}