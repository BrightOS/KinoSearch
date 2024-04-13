package ru.bashcony.kinosearch.data.image.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.image.ImagesMapper.toEntity
import ru.bashcony.kinosearch.data.image.remote.api.ImageApi
import ru.bashcony.kinosearch.data.image.remote.dto.ImagesResponse
import ru.bashcony.kinosearch.domain.image.ImageRepository
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.domain.image.entity.ImagesEntity
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi
) : ImageRepository {
    override fun getImagesByMovie(
        movieId: Int,
        limit: Int,
        page: Int
    ): Single<ImagesEntity> =
        imageApi.getImagesByMovie(
            movieId = movieId,
            limit = limit,
            page = page
        ).map { it.toEntity() }
}