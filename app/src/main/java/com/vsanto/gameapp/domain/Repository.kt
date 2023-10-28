package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.Game

interface Repository {
    suspend fun searchGames(name: String): List<Game>?
}