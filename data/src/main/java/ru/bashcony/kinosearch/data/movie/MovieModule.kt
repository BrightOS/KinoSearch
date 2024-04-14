package ru.bashcony.kinosearch.data.movie

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.movie.db.MovieDao
import ru.bashcony.kinosearch.data.movie.db.MovieLocalDatabase
import ru.bashcony.kinosearch.data.movie.remote.api.MovieApi
import ru.bashcony.kinosearch.data.movie.repository.MovieLocalRepositoryImpl
import ru.bashcony.kinosearch.data.movie.repository.MovieRepositoryImpl
import ru.bashcony.kinosearch.domain.movie.MovieLocalRepository
import ru.bashcony.kinosearch.domain.movie.MovieRepository
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository =
        MovieRepositoryImpl(movieApi)

    @Singleton
    @Provides
    fun provideMovieLocalRepository(movieDao: MovieDao): MovieLocalRepository =
        MovieLocalRepositoryImpl(movieDao)

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieLocalDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideMovieLocalDatabase(@ApplicationContext context: Context): MovieLocalDatabase =
        Room.databaseBuilder(context, MovieLocalDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                println("SQL Query: $sqlQuery SQL Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())
            .build()

}