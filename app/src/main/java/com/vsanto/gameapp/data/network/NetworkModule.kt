package com.vsanto.gameapp.data.network

import com.vsanto.gameapp.BuildConfig.IGDB_BASE_URL

import com.vsanto.gameapp.data.RepositoryImpl
import com.vsanto.gameapp.data.core.interceptors.AuthInterceptor
import com.vsanto.gameapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IGDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideIGDBApiService(retrofit: Retrofit): IGDBApiService {
        return retrofit.create(IGDBApiService::class.java)
    }

    @Provides
    fun provideRepository(iGDBApiService: IGDBApiService): Repository {
        return RepositoryImpl(iGDBApiService)
    }

}