package ru.bashcony.kinosearch.domain.season.entity

import androidx.annotation.Keep
import ru.bashcony.kinosearch.domain.movie.entity.MovieImageEntity

@Keep
data class SeasonEntity(
    val movieId: Int?,
    val number: Int?,
    val episodesCount: Int?,
    val episodes: List<EpisodeEntity>?,
    val poster: MovieImageEntity?,
    val name: String?,
    val enName: String?,
    val duration: Int?,
    val description: String?,
    val enDescription: String?,
    val airDate: String?,
    val updatedAt: String?,
    val createdAt: String?
)