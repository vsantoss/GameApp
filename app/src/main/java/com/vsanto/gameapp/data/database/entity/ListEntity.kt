package com.vsanto.gameapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vsanto.gameapp.domain.model.GameList

@Entity(tableName = "list_table")
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id") var listId: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") val description: String,
) {

    fun toDomain(): GameList {
        return GameList(
            id = listId,
            title = title,
            description = description,
            gamesIds = emptyList(),
            games = emptyList()
        )
    }

}

fun GameList.toEntity(): ListEntity {
    return ListEntity(id, title, description)
}