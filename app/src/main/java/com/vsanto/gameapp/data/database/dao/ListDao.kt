package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vsanto.gameapp.data.database.entity.ListEntity

@Dao
interface ListDao {

    @Query("SELECT * FROM list_table ORDER BY id DESC")
    suspend fun getAllList(): List<ListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: ListEntity)

    @Query("DELETE FROM list_table WHERE id = :id")
    suspend fun delete(id: Int)

}