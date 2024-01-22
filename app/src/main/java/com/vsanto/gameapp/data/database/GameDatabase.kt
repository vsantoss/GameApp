package com.vsanto.gameapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vsanto.gameapp.data.database.dao.ListDao
import com.vsanto.gameapp.data.database.dao.ListGameDao
import com.vsanto.gameapp.data.database.dao.RecentSearchDao
import com.vsanto.gameapp.data.database.dao.UserGameDao
import com.vsanto.gameapp.data.database.entity.ListEntity
import com.vsanto.gameapp.data.database.entity.ListGameCrossRef
import com.vsanto.gameapp.data.database.entity.RecentSearchEntity
import com.vsanto.gameapp.data.database.entity.UserGameEntity

@Database(
    entities = [
        UserGameEntity::class,
        RecentSearchEntity::class,
        ListEntity::class,
        ListGameCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getUserGameDao(): UserGameDao

    abstract fun getRecentSearchDao(): RecentSearchDao

    abstract fun getListDao(): ListDao

    abstract fun getListGameDao(): ListGameDao

}