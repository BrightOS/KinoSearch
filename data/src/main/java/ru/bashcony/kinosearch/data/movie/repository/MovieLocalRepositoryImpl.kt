package ru.bashcony.kinosearch.data.movie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import io.reactivex.Single
import ru.bashcony.kinosearch.data.movie.MoviesMapper.toEntity
import ru.bashcony.kinosearch.data.movie.MoviesMapper.toLocalModel
import ru.bashcony.kinosearch.data.movie.db.MovieDao
import ru.bashcony.kinosearch.domain.movie.MovieLocalRepository
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalRepository {
    override fun insertMovie(movie: MovieEntity): Long {
        println("ROOM SHIT MLR IMPL")
        println("ROOM SHIT BEFORE $movie")
        val movieModel = movie.toLocalModel()
        println("ROOM SHIT $movieModel")
        return movieDao.insertMovie(movieModel)
    }

    override fun getAllMovies() = movieDao.getAllMovies()
        .switchMap { MediatorLiveData(it.map { it.toEntity() }) }

    override fun removeMovie(movie: MovieEntity) =
        movieDao.removeMovie(movie.toLocalModel())

    override fun removeAllFromDb() =
        movieDao.removeAllFromDb()
}