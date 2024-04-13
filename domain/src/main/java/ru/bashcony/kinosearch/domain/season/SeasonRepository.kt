package ru.bashcony.kinosearch.domain.season

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.season.entity.SeasonsEntity

interface SeasonRepository {
    fun getSeasonsOfMovie(movieId: Int, limit: Int, page: Int): Single<SeasonsEntity>
}