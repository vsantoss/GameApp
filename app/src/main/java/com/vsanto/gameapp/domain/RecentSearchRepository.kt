package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.RecentSearch

interface RecentSearchRepository {

    suspend fun getRecentSearches(): List<RecentSearch>?

    suspend fun addRecentSearch(recentSearch: RecentSearch)

    suspend fun removeRecentSearch(id: Int)

}