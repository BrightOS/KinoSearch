package ru.bashcony.kinosearch.data.person.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.person.remote.dto.PersonsResponse

interface PersonApi {
    @GET("person")
    fun getPersonsByMovie(
        @Query("movies.id") moviesId: List<Int>,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("selectFields") selectFields: List<String> = listOf(
            "profession",
            "id",
            "name",
            "enName",
            "photo",
            "sex"
        )
    ): Single<Response<PersonsResponse>>

    @GET("person/{id}")
    fun getPersonById(@Path("id") personId: Int): Single<Response<PersonResponse>>
}