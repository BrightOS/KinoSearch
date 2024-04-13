package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class YearsResponse (
    @SerializedName("start") val start: String? = null,
    @SerializedName("end") val end: String? = null,
)