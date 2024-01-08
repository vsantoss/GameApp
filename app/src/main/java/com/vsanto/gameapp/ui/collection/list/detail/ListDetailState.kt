package com.vsanto.gameapp.ui.collection.list.detail

import com.vsanto.gameapp.domain.model.GameList

sealed class ListDetailState {

    data object Loading : ListDetailState()

    data class Error(val error: String) : ListDetailState()

    data class Success(val list: GameList) : ListDetailState()

}