package com.vsanto.gameapp.ui.search

import com.vsanto.gameapp.domain.model.Game

sealed class SearchState {
    data object Init: SearchState()
    data object Loading : SearchState()
    data class Error(val error: String) : SearchState()
    data class Success(val games: List<Game>) : SearchState()
}