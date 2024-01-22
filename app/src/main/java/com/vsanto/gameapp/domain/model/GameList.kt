package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class GameList(
    val id: Int,
    val title: String,
    val description: String,
    val gamesIds : List<Int>,
    var games: List<GameSummary>
) : Serializable