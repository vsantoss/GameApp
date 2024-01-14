package com.vsanto.gameapp.ui.collection.list.addtolist

sealed class AddToListState {

    data object Init : AddToListState()

    data object Loading : AddToListState()

    data class Error(val error: String) : AddToListState()

    data object Success : AddToListState()

}