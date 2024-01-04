package com.vsanto.gameapp.di

import android.content.Context
import androidx.room.Room
import com.vsanto.gameapp.data.database.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val GAME_DATABASE_NAME = "game_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, GameDatabase::class.java, GAME_DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideUserGameDao(db: GameDatabase) = db.getUserGameDao()

    @Singleton
    @Provides
    fun provideRecentSearchDao(db: GameDatabase) = db.getRecentSearchDao()

    @Singleton
    @Provides
    fun provideListDao(db: GameDatabase) = db.getListDao()

}