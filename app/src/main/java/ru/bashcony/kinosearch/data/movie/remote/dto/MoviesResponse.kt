package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("docs") val docs: List<MovieResponse>,
    @SerializedName("total") val total: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
)