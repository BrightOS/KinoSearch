package ru.bashcony.kinosearch.domain.image

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.image.entity.ImagesEntity

interface ImageRepository {
    fun getImagesByMovie(movieId: Int, limit: Int, page: Int): Single<ImagesEntity>
}