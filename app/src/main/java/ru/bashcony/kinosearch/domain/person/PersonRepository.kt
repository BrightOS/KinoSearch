package ru.bashcony.kinosearch.domain.person

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.person.remote.dto.PersonsResponse

interface PersonRepository {
    fun getPersonsByMovie(moviesId: List<Int>, limit: Int, page: Int): Single<Response<PersonsResponse>>
    fun getPersonById(personId: Int): Single<Response<PersonResponse>>
}