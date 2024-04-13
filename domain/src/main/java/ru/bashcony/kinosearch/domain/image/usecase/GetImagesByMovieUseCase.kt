package ru.bashcony.kinosearch.domain.image.usecase

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.image.entity.ImagesEntity
import ru.bashcony.kinosearch.domain.image.ImageRepository
import javax.inject.Inject

class GetImagesByMovieUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(movieId: Int, limit: Int, page: Int): Single<ImagesEntity> =
        imageRepository.getImagesByMovie(movieId, limit, page)
}