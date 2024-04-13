package ru.bashcony.kinosearch.data.person

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.person.remote.api.PersonApi
import ru.bashcony.kinosearch.data.person.repository.PersonRepositoryImpl
import ru.bashcony.kinosearch.domain.person.PersonRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class PersonModule {

    @Singleton
    @Provides
    fun providePersonApi(retrofit: Retrofit): PersonApi =
        retrofit.create(PersonApi::class.java)

    @Singleton
    @Provides
    fun provideSeasonRepository(personApi: PersonApi): PersonRepository =
        PersonRepositoryImpl(personApi)

}