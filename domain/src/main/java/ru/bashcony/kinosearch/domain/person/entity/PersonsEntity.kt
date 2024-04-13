package ru.bashcony.kinosearch.domain.person.entity

import androidx.annotation.Keep

@Keep
data class PersonsEntity(
    val docs: List<PersonEntity>?,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?
)