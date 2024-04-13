package ru.bashcony.kinosearch.domain.movie.entity

import androidx.annotation.Keep

@Keep
data class MovieEntity(
    val id: Int?,
    val type: String?,
    val name: String?,
    val alternativeName: String?,
    val enName: String?,
    val ratingMpaa: String?,
    val rating: RatingEntity?,
    val ageRating: Int?,
    val description: String?,
    val votes: VotesEntity?,
    val premiere: PremiereEntity?,
    val slogan: String?,
    val year: Int?,
    val facts: List<FactEntity>?,
    val genres: List<NameEntity>?,
    val countries: List<NameEntity>?,
    val seasonsInfo: List<SeasonInfoEntity>?,
    val poster: MovieImageEntity?,
    val backdrop: MovieImageEntity?,
    val logo: MovieImageEntity?,
    val trailersEntity: TrailersEntity?,
    val names: List<NameEntity>?,
    val similarMovies: List<LinkedMovieEntity>?,
    val sequelsAndPrequels: List<LinkedMovieEntity>?,
    val releaseYears: List<YearsEntity>?,
    val isSeries: Boolean?,
    val seriesLength: Int?,
    val totalSeriesLength: Int?,
    val movieLength: Int?,
    val status: String?,
    val top10: Int?,
    val top250: Int?,
    val ticketsOnSale: Boolean?,
    val updatedAt: String?,
    val createdAt: String?
)