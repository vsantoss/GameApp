package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vsanto.gameapp.data.database.entity.UserGameEntity

@Dao
interface UserGameDao {

    @Query("SELECT * FROM user_game_table")
    suspend fun getAllUserGames(): List<UserGameEntity>

    @Query("SELECT * FROM user_game_table WHERE game_id = :gameId")
    suspend fun getUserGameByGameId(gameId: Int): UserGameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: UserGameEntity)

    @Query("DELETE FROM user_game_table WHERE game_id = :gameId")
    suspend fun deleteUserGameByGameId(gameId: Int)

}