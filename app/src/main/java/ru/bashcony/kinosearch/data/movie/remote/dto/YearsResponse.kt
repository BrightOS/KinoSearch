package ru.bashcony.kinosearch.data.movie.remote.dto

import com.google.gson.annotations.SerializedName

data class YearsResponse (
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?,
)