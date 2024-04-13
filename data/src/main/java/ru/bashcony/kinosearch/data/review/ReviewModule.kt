package ru.bashcony.kinosearch.data.review

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.person.remote.api.PersonApi
import ru.bashcony.kinosearch.data.person.repository.PersonRepositoryImpl
import ru.bashcony.kinosearch.data.review.remote.api.ReviewApi
import ru.bashcony.kinosearch.data.review.repository.ReviewRepositoryImpl
import ru.bashcony.kinosearch.domain.person.PersonRepository
import ru.bashcony.kinosearch.domain.review.ReviewRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ReviewModule {

    @Singleton
    @Provides
    fun provideReviewApi(retrofit: Retrofit): ReviewApi =
        retrofit.create(ReviewApi::class.java)

    @Singleton
    @Provides
    fun provideReviewRepository(reviewApi: ReviewApi): ReviewRepository =
        ReviewRepositoryImpl(reviewApi)

}