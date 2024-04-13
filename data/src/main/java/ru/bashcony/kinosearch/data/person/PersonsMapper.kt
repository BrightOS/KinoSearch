package ru.bashcony.kinosearch.data.person

import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.person.remote.dto.PersonsResponse
import ru.bashcony.kinosearch.data.person.remote.dto.ProfessionResponse
import ru.bashcony.kinosearch.domain.person.entity.PersonEntity
import ru.bashcony.kinosearch.domain.person.entity.PersonsEntity
import ru.bashcony.kinosearch.domain.person.entity.ProfessionEntity

object PersonsMapper {
    fun PersonResponse.toEntity() =
        PersonEntity(
            id ?: -1,
            age,
            enName,
            name,
            photo,
            sex,
            profession.orEmpty().map { it.toEntity() }
        )
    fun PersonsResponse.toEntity() =
        PersonsEntity(
            docs.orEmpty().map { it.toEntity() },
            total,
            limit,
            page,
            pages
        )

    fun ProfessionResponse.toEntity() =
        ProfessionEntity(
            value.orEmpty()
        )
}