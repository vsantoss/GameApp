package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class UserGame(
    val gameId: Int,
    val state: UserGameState
) : Serializable