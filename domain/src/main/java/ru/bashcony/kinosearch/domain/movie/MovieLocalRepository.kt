package ru.bashcony.kinosearch.domain.movie

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity

interface MovieLocalRepository {
    fun insertMovie(movie: MovieEntity): Long
    fun getAllMovies(): LiveData<List<MovieEntity>>
    fun removeMovie(movie: MovieEntity)
    fun removeAllFromDb()
}