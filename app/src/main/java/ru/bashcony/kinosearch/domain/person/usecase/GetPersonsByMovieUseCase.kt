package ru.bashcony.kinosearch.domain.person.usecase

import ru.bashcony.kinosearch.domain.person.PersonRepository
import javax.inject.Inject

class GetPersonsByMovieUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {
    operator fun invoke(movieId: Int, limit: Int, page: Int) =
        personRepository.getPersonsByMovie(listOf(movieId), limit, page)
}