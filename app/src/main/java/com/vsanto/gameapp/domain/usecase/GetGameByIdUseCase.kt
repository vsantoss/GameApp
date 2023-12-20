package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.model.GameDetail
import javax.inject.Inject

class GetGameByIdUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(id: Int): GameDetail? {
        val gameDetail: GameDetail? = repository.getGameById(id)

        val userGame = repository.getUserGameByGameId(id)
        gameDetail?.userGame = userGame

        return gameDetail
    }

}