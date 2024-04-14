package ru.bashcony.kinosearch.data.movie.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Database for local storage of searching history.
 */
@Database(entities = [MovieLocalModel::class], version = 2, exportSchema = true)
abstract class MovieLocalDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}