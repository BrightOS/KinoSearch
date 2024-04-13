package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NameResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("type") val type: String?,
)
