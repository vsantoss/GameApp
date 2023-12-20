package com.vsanto.gameapp.ui.collection

import com.vsanto.gameapp.domain.model.Library

sealed class CollectionState {
    data object Loading : CollectionState()
    data class Error(val error: String) : CollectionState()
    data class Success(val library: Library) : CollectionState()
}