package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vsanto.gameapp.data.database.entity.ListGameCrossRef

@Dao
interface ListGameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(listgames: List<ListGameCrossRef>)

    @Query("DELETE FROM list_game_table WHERE list_Id = :listId AND game_id = :gameId")
    suspend fun delete(listId: Int, gameId: Int)

}