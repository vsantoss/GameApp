package com.vsanto.gameapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vsanto.gameapp.data.database.entity.RecentSearchEntity

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recent_search_table ORDER BY id DESC")
    suspend fun getAllRecentSearches(): List<RecentSearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: RecentSearchEntity)

    @Query("DELETE FROM recent_search_table WHERE id = :id")
    suspend fun deleteRecentSearch(id: Int)

    @Query("DELETE FROM recent_search_table")
    suspend fun deleteAllRecentSearches()

}
