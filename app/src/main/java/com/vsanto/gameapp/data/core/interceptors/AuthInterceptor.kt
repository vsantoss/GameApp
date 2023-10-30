package com.vsanto.gameapp.data.core.interceptors

import com.vsanto.gameapp.BuildConfig.IGDB_AUTHORIZATION_BEARER
import com.vsanto.gameapp.BuildConfig.IGDB_CLIENT_ID
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Client-ID", IGDB_CLIENT_ID)
            .addHeader("Authorization", IGDB_AUTHORIZATION_BEARER)
            .addHeader("Content-Type", "text/plain")
            .build()

        return chain.proceed(request)
    }
}