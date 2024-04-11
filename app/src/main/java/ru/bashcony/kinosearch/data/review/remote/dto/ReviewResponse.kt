package ru.bashcony.kinosearch.data.review.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("movieId") val movieId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("review") val review: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("userRating") val userRating: Int? = null,
    @SerializedName("authorId") val authorId: Int? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null
)
