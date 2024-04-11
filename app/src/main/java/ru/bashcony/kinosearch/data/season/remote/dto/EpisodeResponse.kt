package ru.bashcony.kinosearch.data.season.remote.dto

import com.google.gson.annotations.SerializedName
import ru.bashcony.kinosearch.data.movie.remote.dto.ImageResponse

data class EpisodeResponse(
    @SerializedName("number") val number: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("enName") val enName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("still") val still: ImageResponse?,
    @SerializedName("airDate") val airDate: String?,
    @SerializedName("enDescription") val enDescription: String?,
)
