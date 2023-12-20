package com.vsanto.gameapp.data

import android.util.Log
import com.vsanto.gameapp.data.database.dao.RecentSearchDao
import com.vsanto.gameapp.data.database.entity.toEntity
import com.vsanto.gameapp.domain.RecentSearchRepository
import com.vsanto.gameapp.domain.model.RecentSearch
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
) : RecentSearchRepository {

    override suspend fun getRecentSearches(): List<RecentSearch>? {
        runCatching { recentSearchDao.getAllRecentSearches() }
            .onSuccess { return it.map { search -> search.toDomain() } }
            .onFailure { Log.e("recent_search", "Ha ocurrido el error ${it.message}") }

        return null
    }

    override suspend fun addRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insert(recentSearch.toEntity())
    }

    override suspend fun removeRecentSearch(id: Int) {
        recentSearchDao.deleteRecentSearch(id)
    }

}