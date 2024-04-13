package ru.bashcony.kinosearch.domain.season.entity

import androidx.annotation.Keep
import ru.bashcony.kinosearch.domain.movie.entity.MovieImageEntity

@Keep
data class EpisodeEntity(
    val number: Int?,
    val name: String?,
    val enName: String?,
    val description: String?,
    val still: MovieImageEntity?,
    val airDate: String?,
    val enDescription: String?,
)
