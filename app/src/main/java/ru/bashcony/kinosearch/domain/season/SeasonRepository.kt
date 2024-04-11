package ru.bashcony.kinosearch.domain.season

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonsResponse

interface SeasonRepository {
    fun getSeasonsOfMovie(movieId: Int, limit: Int, page: Int): Single<Response<SeasonsResponse>>
}