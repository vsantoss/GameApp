package com.vsanto.gameapp.ui.common.gamelist

import com.vsanto.gameapp.domain.model.GameSummary

sealed class GameListState {

    data object Loading : GameListState()
    data class Error(val error: String) : GameListState()
    data class Success(val games: List<GameSummary>) : GameListState()

}