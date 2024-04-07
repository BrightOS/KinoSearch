package ru.bashcony.kinosearch.data.common.utils

import okhttp3.Interceptor
import okhttp3.Response
import ru.bashcony.kinosearch.infra.utils.SharedPrefs

class RequestInterceptor constructor(private val sharedPrefs: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefs.token
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}