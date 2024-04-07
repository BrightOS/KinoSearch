package ru.bashcony.kinosearch.data.common.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bashcony.kinosearch.infra.utils.SharedPrefs

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) =
        SharedPrefs(
            sharedPreferences = context.getSharedPreferences(
                SharedPrefs.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
}