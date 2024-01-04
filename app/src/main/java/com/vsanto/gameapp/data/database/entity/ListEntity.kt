package com.vsanto.gameapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vsanto.gameapp.domain.model.GameList

@Entity(tableName = "list_table")
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String
) {

    fun toDomain(): GameList {
        return GameList(
            id = id,
            title = title,
            games = emptyList()
        )
    }

}

fun GameList.toEntity(): ListEntity {
    return ListEntity(id, title)
}