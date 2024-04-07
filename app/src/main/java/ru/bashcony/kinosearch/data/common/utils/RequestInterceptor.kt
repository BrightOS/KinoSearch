package ru.bashcony.kinosearch.data.common.utils

import okhttp3.Interceptor
import okhttp3.Response
import ru.bashcony.kinosearch.BuildConfig
import ru.bashcony.kinosearch.infra.utils.SharedPrefs

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.API_KEY
        val newRequest = chain.request().newBuilder()
            .addHeader("X-API-KEY", token)
            .build()
        return chain.proceed(newRequest)
    }
}