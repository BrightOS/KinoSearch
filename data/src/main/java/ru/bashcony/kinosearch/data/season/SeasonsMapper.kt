package ru.bashcony.kinosearch.data.season

import ru.bashcony.kinosearch.data.movie.MoviesMapper.toEntity
import ru.bashcony.kinosearch.data.movie.remote.dto.ImageResponse
import ru.bashcony.kinosearch.data.season.remote.dto.EpisodeResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse
import ru.bashcony.kinosearch.domain.season.entity.EpisodeEntity
import ru.bashcony.kinosearch.domain.season.entity.SeasonEntity
import ru.bashcony.kinosearch.domain.season.entity.SeasonsEntity

object SeasonsMapper {
    fun EpisodeResponse.toEntity() =
        EpisodeEntity(
            number,
            name,
            enName,
            description,
            (still ?: ImageResponse()).toEntity(),
            airDate,
            enDescription.orEmpty()
        )

    fun SeasonResponse.toEntity() =
        SeasonEntity(
            movieId ?: -1,
            number,
            episodesCount,
            episodes.orEmpty().map { it.toEntity() },
            (poster ?: ImageResponse()).toEntity(),
            name,
            enName,
            duration,
            description,
            enDescription,
            airDate,
            updatedAt,
            createdAt.orEmpty()
        )

    fun SeasonsResponse.toEntity() =
        SeasonsEntity(
            docs.orEmpty().map { it.toEntity() },
            total,
            limit,
            page,
            pages
        )
}