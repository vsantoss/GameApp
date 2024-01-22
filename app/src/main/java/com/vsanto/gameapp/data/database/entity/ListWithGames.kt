package com.vsanto.gameapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vsanto.gameapp.domain.model.GameList

data class ListWithGames(
    @Embedded
    val list: ListEntity,
    @Relation(
        parentColumn = "list_id",
        entityColumn = "game_id",
        associateBy = Junction(ListGameCrossRef::class)
    )
    val games: List<UserGameEntity>
){
    fun toDomain(): GameList {
        return GameList(
            id = list.listId,
            title = list.title,
            description = list.description,
            gamesIds = games.map { it.gameId },
            games = emptyList()
        )
    }
}