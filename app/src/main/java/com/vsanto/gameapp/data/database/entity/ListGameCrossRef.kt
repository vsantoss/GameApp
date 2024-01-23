package com.vsanto.gameapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "list_game_table", primaryKeys = ["list_id", "game_id"])
data class ListGameCrossRef(
    @ColumnInfo(name = "list_id") val listId: Int,
    @ColumnInfo(name = "game_id") val gameId: Int
)