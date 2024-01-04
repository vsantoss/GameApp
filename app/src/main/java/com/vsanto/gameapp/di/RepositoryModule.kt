package com.vsanto.gameapp.di

import com.vsanto.gameapp.data.GameRepositoryImpl
import com.vsanto.gameapp.data.ListRepositoryImpl
import com.vsanto.gameapp.data.RecentSearchRepositoryImpl
import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.RecentSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGameRepository(gameRepository: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun provideRecentSearchRepository(recentSearchRepository: RecentSearchRepositoryImpl): RecentSearchRepository

    @Binds
    abstract fun provideListRepository(listRepository: ListRepositoryImpl): ListRepository

}