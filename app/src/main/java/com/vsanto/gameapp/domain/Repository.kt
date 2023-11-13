package com.vsanto.gameapp.domain

import com.vsanto.gameapp.domain.model.CompanyDetail
import com.vsanto.gameapp.domain.model.GameDetail
import com.vsanto.gameapp.domain.model.GameSummary

interface Repository {
    suspend fun searchGames(name: String): List<GameSummary>?

    suspend fun getGameById(id: Int): GameDetail?

    suspend fun getCompanyById(id: Int): CompanyDetail?

}