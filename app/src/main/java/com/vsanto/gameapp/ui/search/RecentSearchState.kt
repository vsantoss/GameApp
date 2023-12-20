package com.vsanto.gameapp.ui.search

import com.vsanto.gameapp.domain.model.RecentSearch

sealed class RecentSearchState {

    data object Init : RecentSearchState()

    data object Loading : RecentSearchState()

    data class Error(val error: String) : RecentSearchState()

    data class Success(val searches: List<RecentSearch>) : RecentSearchState()

}