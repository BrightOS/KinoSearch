package ru.bashcony.kinosearch.data.image

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.bashcony.kinosearch.data.common.module.NetworkModule
import ru.bashcony.kinosearch.data.image.remote.api.ImageApi
import ru.bashcony.kinosearch.data.image.repository.ImageRepositoryImpl
import ru.bashcony.kinosearch.domain.image.ImageRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ImageModule {

    @Singleton
    @Provides
    fun provideImageApi(retrofit: Retrofit): ImageApi =
        retrofit.create(ImageApi::class.java)

    @Singleton
    @Provides
    fun provideImageRepository(imageApi: ImageApi): ImageRepository =
        ImageRepositoryImpl(imageApi)

}