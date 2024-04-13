package ru.bashcony.kinosearch.domain.person.entity

import androidx.annotation.Keep

@Keep
data class PersonEntity(
    val id: Int?,
    val age: Int?,
    val enName: String?,
    val name: String?,
    val photo: String?,
    val sex: String?,
    val profession: List<ProfessionEntity>?
)
