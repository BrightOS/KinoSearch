package ru.bashcony.kinosearch.data.season.remote.dto

import com.google.gson.annotations.SerializedName
import ru.bashcony.kinosearch.data.movie.remote.dto.ImageResponse

data class SeasonResponse(
    @SerializedName("movieId") val movieId: Int? = null,
    @SerializedName("number") val number: Int? = null,
    @SerializedName("episodesCount") val episodesCount: Int? = null,
    @SerializedName("episodes") val episodes: List<EpisodeResponse>? = null,
    @SerializedName("poster") val poster: ImageResponse? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("enName") val enName: String? = null,
    @SerializedName("duration") val duration: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("enDescription") val enDescription: String? = null,
    @SerializedName("airDate") val airDate: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)