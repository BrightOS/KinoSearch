package ru.bashcony.kinosearch.data.movie

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.movie.remote.api.MovieApi
import ru.bashcony.kinosearch.data.movie.repository.MovieRepositoryImpl
import ru.bashcony.kinosearch.domain.movie.MovieRepository
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

}