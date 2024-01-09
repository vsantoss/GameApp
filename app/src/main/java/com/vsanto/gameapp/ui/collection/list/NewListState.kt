package com.vsanto.gameapp.ui.collection.list

sealed class NewListState {

    data object Init : NewListState()

    data object Loading : NewListState()

    data class Error(val error: String) : NewListState()

    data class Success(val id: Int) : NewListState()

}