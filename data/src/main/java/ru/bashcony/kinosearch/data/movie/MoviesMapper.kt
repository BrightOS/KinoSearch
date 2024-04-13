package ru.bashcony.kinosearch.data.movie

import ru.bashcony.kinosearch.data.movie.MoviesMapper.toEntity
import ru.bashcony.kinosearch.data.movie.remote.dto.FactResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.ImageResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.LinkedMovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MoviesResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.NameResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.PremiereResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.RatingResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.SeasonInfoResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.TrailersResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.VideoResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.VotesResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.YearsResponse
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.domain.movie.entity.FactEntity
import ru.bashcony.kinosearch.domain.movie.entity.LinkedMovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.MovieImageEntity
import ru.bashcony.kinosearch.domain.movie.entity.MoviesEntity
import ru.bashcony.kinosearch.domain.movie.entity.NameEntity
import ru.bashcony.kinosearch.domain.movie.entity.PremiereEntity
import ru.bashcony.kinosearch.domain.movie.entity.RatingEntity
import ru.bashcony.kinosearch.domain.movie.entity.SeasonInfoEntity
import ru.bashcony.kinosearch.domain.movie.entity.TrailersEntity
import ru.bashcony.kinosearch.domain.movie.entity.VideoEntity
import ru.bashcony.kinosearch.domain.movie.entity.VotesEntity
import ru.bashcony.kinosearch.domain.movie.entity.YearsEntity

object MoviesMapper {
    fun FactResponse.toEntity() =
        FactEntity(
            value,
            type,
            spoiler
        )

    fun ImageResponse.toEntity() =
        MovieImageEntity(
            url,
            previewUrl.orEmpty()
        )

    fun LinkedMovieResponse.toEntity() =
        LinkedMovieEntity(
            id ?: -1,
            name,
            enName,
            alternativeName,
            type,
            (poster ?: ImageResponse()).toEntity()
        )

    fun MovieResponse.toEntity() = MovieEntity(
        id ?: -1,
        type,
        name,
        alternativeName,
        enName,
        ratingMpaa,
        (rating ?: RatingResponse()).toEntity(),
        ageRating,
        description,
        (votes ?: VotesResponse()).toEntity(),
        (premiere ?: PremiereResponse()).toEntity(),
        slogan,
        year,
        (facts.orEmpty()).map { it.toEntity() },
        (genres.orEmpty()).map { it.toEntity() },
        (countries.orEmpty()).map { it.toEntity() },
        (seasonsInfo.orEmpty()).map { it.toEntity() },
        (poster ?: ImageResponse()).toEntity(),
        (backdrop ?: ImageResponse()).toEntity(),
        (logo ?: ImageResponse()).toEntity(),
        (trailersResponse ?: TrailersResponse()).toEntity(),
        (names.orEmpty()).map { it.toEntity() },
        (similarMovies.orEmpty()).map { it.toEntity() },
        (sequelsAndPrequels.orEmpty()).map { it.toEntity() },
        (releaseYears.orEmpty()).map { it.toEntity() },
        isSeries ?: false,
        seriesLength,
        totalSeriesLength,
        movieLength,
        status,
        top10,
        top250,
        ticketsOnSale ?: false,
        updatedAt,
        createdAt
    )

    fun MoviesResponse.toEntity() =
        MoviesEntity(
            (docs.orEmpty()).map { it.toEntity() },
            total,
            limit,
            page,
            pages
        )

    fun NameResponse.toEntity() =
        NameEntity(
            name,
            language,
            type
        )

    fun PremiereResponse.toEntity() =
        PremiereEntity(
            world,
            russia,
            digital,
            cinema
        )

    fun RatingResponse.toEntity() =
        RatingEntity(
            kp,
            imdb,
            tmdb,
            filmCritics,
            russianFilmCritics,
            await
        )

    fun SeasonInfoResponse.toEntity() =
        SeasonInfoEntity(
            number,
            episodesCount
        )

    fun TrailersResponse.toEntity() =
        TrailersEntity(
            (trailers.orEmpty()).map { it.toEntity() }
        )

    fun VideoResponse.toEntity() =
        VideoEntity(
            url,
            name,
            site,
            size,
            type
        )

    fun VotesResponse.toEntity() =
        VotesEntity(
            kp,
            imdb,
            filmCritics,
            russianFilmCritics,
            await
        )

    fun YearsResponse.toEntity() =
        YearsEntity(
            start,
            end
        )
}