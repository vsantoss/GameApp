package com.vsanto.gameapp.ui.gamedetail

import com.vsanto.gameapp.domain.model.GameDetail

sealed class GameDetailState {
    data object Loading : GameDetailState()
    data class Error(val error: String) : GameDetailState()
    data class Success(val game: GameDetail) : GameDetailState()
}