package com.vsanto.gameapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vsanto.gameapp.domain.model.UserGame
import com.vsanto.gameapp.domain.model.UserGameState

@Entity(tableName = "user_game_table")
data class UserGameEntity(
    @PrimaryKey
    @ColumnInfo(name = "game_id") var gameId: Int,
    @ColumnInfo(name = "state") var state: Int
) {
    fun toDomain(): UserGame {
        return UserGame(
            gameId = gameId,
            state = UserGameState.toState(state)
        )
    }

}

fun UserGame.toEntity(): UserGameEntity {
    return UserGameEntity(gameId, UserGameState.toCode(state))
}

