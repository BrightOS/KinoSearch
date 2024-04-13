package ru.bashcony.kinosearch.data.season

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.season.remote.api.SeasonApi
import ru.bashcony.kinosearch.data.season.repository.SeasonRepositoryImpl
import ru.bashcony.kinosearch.domain.season.SeasonRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class SeasonModule {

    @Singleton
    @Provides
    fun provideSeasonApi(retrofit: Retrofit): SeasonApi =
        retrofit.create(SeasonApi::class.java)

    @Singleton
    @Provides
    fun provideSeasonRepository(seasonApi: SeasonApi): SeasonRepository =
        SeasonRepositoryImpl(seasonApi)

}