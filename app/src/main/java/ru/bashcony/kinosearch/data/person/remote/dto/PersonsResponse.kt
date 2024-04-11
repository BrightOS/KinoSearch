package ru.bashcony.kinosearch.data.person.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonsResponse(
    @SerializedName("docs") val docs: List<PersonResponse>? = listOf(),
    @SerializedName("total") val total: Int? = null,
    @SerializedName("limit") val limit: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("pages") val pages: Int? = null
)