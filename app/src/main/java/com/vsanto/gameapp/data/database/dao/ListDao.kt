package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vsanto.gameapp.data.database.entity.ListEntity
import com.vsanto.gameapp.data.database.entity.ListWithGames

@Dao
interface ListDao {

    @Query("SELECT * FROM list_table ORDER BY list_Id DESC")
    suspend fun getAllList(): List<ListEntity>

    @Query("SELECT * FROM list_table WHERE list_Id = :id")
    suspend fun getList(id: Int): ListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: ListEntity): Long

    @Query("DELETE FROM list_table WHERE list_Id = :id")
    suspend fun delete(id: Int)

    @Transaction
    @Query("SELECT * FROM list_table ORDER BY list_Id DESC")
    fun getListsWithGames(): List<ListWithGames>

}