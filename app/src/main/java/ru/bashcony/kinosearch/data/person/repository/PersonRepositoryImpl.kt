package ru.bashcony.kinosearch.data.person.repository

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.person.remote.api.PersonApi
import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.person.remote.dto.PersonsResponse
import ru.bashcony.kinosearch.domain.person.PersonRepository
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi
) : PersonRepository {
    override fun getPersonsByMovie(
        moviesId: List<Int>,
        limit: Int,
        page: Int
    ): Single<Response<PersonsResponse>> =
        personApi.getPersonsByMovie(moviesId = moviesId, limit = limit, page = page)

    override fun getPersonById(personId: Int): Single<Response<PersonResponse>> =
        personApi.getPersonById(personId)
}