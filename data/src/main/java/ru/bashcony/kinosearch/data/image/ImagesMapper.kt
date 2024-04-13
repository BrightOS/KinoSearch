package ru.bashcony.kinosearch.data.image

import ru.bashcony.kinosearch.data.image.remote.dto.ImageResponse
import ru.bashcony.kinosearch.data.image.remote.dto.ImagesResponse
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.domain.image.entity.ImagesEntity

object ImagesMapper {

    fun ImagesResponse.toEntity() =
        ImagesEntity(
            (docs.orEmpty()).map { it.toEntity() },
            total,
            limit,
            page,
            pages
        )

    fun ImageResponse.toEntity() =
        ImageEntity(
            movieId ?: -1,
            type,
            language,
            url,
            previewUrl,
            height ?: -1,
            width ?: -1,
            updatedAt,
            createdAt.orEmpty()
        )
}