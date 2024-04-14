package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ValueResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("slug") val slug: String? = null
)