package ru.bashcony.kinosearch.data.season.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.movie.remote.api.MovieApi
import ru.bashcony.kinosearch.data.season.remote.api.SeasonApi
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse
import ru.bashcony.kinosearch.domain.season.SeasonRepository
import javax.inject.Inject

class SeasonRepositoryImpl @Inject constructor(
    private val seasonApi: SeasonApi
) : SeasonRepository {
    override fun getSeasonsOfMovie(movieId: Int, limit: Int, page: Int) =
        seasonApi.getSeasonsByMovie(movieId = movieId, limit = limit, page = page)
}