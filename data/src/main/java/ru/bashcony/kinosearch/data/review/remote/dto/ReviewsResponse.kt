package ru.bashcony.kinosearch.data.review.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ReviewsResponse(
    @SerializedName("docs") val docs: List<ReviewResponse>? = listOf(),
    @SerializedName("total") val total: Int? = null,
    @SerializedName("limit") val limit: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("pages") val pages: Int? = null
)