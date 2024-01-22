package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.vsanto.gameapp.data.database.entity.ListGameCrossRef

@Dao
interface ListGameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(listgames: List<ListGameCrossRef>)

}