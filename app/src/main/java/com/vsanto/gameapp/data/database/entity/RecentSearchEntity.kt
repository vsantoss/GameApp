package com.vsanto.gameapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vsanto.gameapp.domain.model.RecentSearch

@Entity(tableName = "recent_search_table")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "query") var query: String
) {

    fun toDomain(): RecentSearch {
        return RecentSearch(
            id = id,
            query = query
        )
    }

}

fun RecentSearch.toEntity(): RecentSearchEntity {
    return RecentSearchEntity(id, query)
}