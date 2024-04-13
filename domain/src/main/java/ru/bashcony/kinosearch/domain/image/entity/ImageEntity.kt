package ru.bashcony.kinosearch.domain.image.entity

import androidx.annotation.Keep

@Keep
data class ImageEntity(
    val movieId: Int?,
    val type: String?,
    val language: String?,
    val url: String?,
    val previewUrl: String?,
    val height: Int?,
    val width: Int?,
    val updatedAt: String?,
    val createdAt: String?
)
