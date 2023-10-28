package com.vsanto.gameapp.data.core.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Client-ID", "8kjgodb5ozfyj3q70rmekr9lioe59z")
            .addHeader("Authorization", "Bearer v3s917uilrqwh5v1b3aj8cgw85y7tl")
            .addHeader("Content-Type", "text/plain")
            .build()

        return chain.proceed(request)
    }
}