package ru.bashcony.kinosearch.domain.person

import io.reactivex.Single
import retrofit2.Response
import ru.bashcony.kinosearch.domain.person.entity.PersonEntity
import ru.bashcony.kinosearch.domain.person.entity.PersonsEntity

interface PersonRepository {
    fun getPersonsByMovie(moviesId: List<Int>, limit: Int, page: Int): Single<PersonsEntity>
    fun getPersonById(personId: Int): Single<PersonEntity>
}