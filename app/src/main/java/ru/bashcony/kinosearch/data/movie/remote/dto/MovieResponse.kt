package ru.bashcony.kinosearch.data.movie.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("alternativeName") val alternativeName: String? = null,
    @SerializedName("ratingMpaa") val ratingMpaa: String? = null,
    @SerializedName("rating") val rating: RatingResponse? = null,
    @SerializedName("ageRating") val ageRating: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("votes") val votes: VotesResponse? = null,
    @SerializedName("premiere") val premiere: PremiereResponse? = null,
    @SerializedName("slogan") val slogan: String? = null,
    @SerializedName("year") val year: Int? = null,
    @SerializedName("facts") val facts: List<FactResponse>? = listOf(),
    @SerializedName("genres") val genres: List<NameResponse>? = listOf(),
    @SerializedName("countries") val countries: List<NameResponse>? = listOf(),
    @SerializedName("seasonsInfo") val seasonsInfo: List<SeasonInfoResponse>? = listOf(),
    @SerializedName("poster") val poster: ImageResponse? = null,
    @SerializedName("backdrop") val backdrop: ImageResponse? = null,
    @SerializedName("logo") val logo: ImageResponse? = null,
    @SerializedName("videos") val trailersResponse: TrailersResponse? = null,
    @SerializedName("names") val names: List<NameResponse>? = listOf(),
    @SerializedName("similarMovies") val similarMovies: List<LinkedMovieResponse>? = listOf(),
    @SerializedName("sequelsAndPrequels") val sequelsAndPrequels: List<LinkedMovieResponse>? = listOf(),
    @SerializedName("releaseYears") val releaseYears: List<YearsResponse>? = listOf(),
    @SerializedName("isSeries") val isSeries: Boolean? = null,
    @SerializedName("seriesLength") val seriesLength: Int? = null,
    @SerializedName("totalSeriesLength") val totalSeriesLength: Int? = null,
    @SerializedName("movieLength") val movieLength: Int? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("top10") val top10: Int? = null,
    @SerializedName("top250") val top250: Int? = null,
    @SerializedName("ticketsOnSale") val ticketsOnSale: Boolean? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)