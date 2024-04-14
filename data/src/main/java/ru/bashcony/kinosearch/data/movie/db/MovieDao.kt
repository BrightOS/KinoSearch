package ru.bashcony.kinosearch.data.movie.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieLocalModel): Long

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): LiveData<List<MovieLocalModel>>

    @Delete
    fun removeMovie(movie: MovieLocalModel)

    @Query("DELETE FROM movie_table")
    fun removeAllFromDb()
}