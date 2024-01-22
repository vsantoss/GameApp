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

    @Transaction
    @Query("SELECT * FROM list_table ORDER BY list_Id DESC")
    fun getListsWithGames(): List<ListWithGames>

    @Query("SELECT * FROM list_table WHERE list_Id = :id")
    suspend fun getListWithGames(id: Int): ListWithGames

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: ListEntity): Long

    @Query("DELETE FROM list_table WHERE list_Id = :id")
    suspend fun delete(id: Int)

}