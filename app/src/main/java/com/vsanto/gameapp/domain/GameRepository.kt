package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.CompanyDetail
import com.vsanto.gameapp.domain.model.GameDetail
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.domain.model.UserGame

interface GameRepository {
    suspend fun searchGames(name: String): List<GameSummary>?

    suspend fun getGameById(id: Int): GameDetail?

    suspend fun getCompanyById(id: Int): CompanyDetail?

    // User Games
    suspend fun addUserGame(userGame: UserGame)

    suspend fun removeUserGame(gameId: Int)

    suspend fun getUserGameByGameId(gameId: Int): UserGame?

    suspend fun getUserGames(): List<UserGame>?

}