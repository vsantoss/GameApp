package com.vsanto.gameapp.ui.collection.list

import com.vsanto.gameapp.domain.model.GameList

sealed class ListState {

    data object Loading : ListState()

    data class Error(val error: String) : ListState()

    data class Success(val lists: List<GameList>) : ListState()

}