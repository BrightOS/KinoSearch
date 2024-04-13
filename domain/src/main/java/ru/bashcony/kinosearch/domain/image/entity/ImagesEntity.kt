package ru.bashcony.kinosearch.domain.image.entity

import androidx.annotation.Keep

@Keep
class ImagesEntity(
    val docs: List<ImageEntity>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)