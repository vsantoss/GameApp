package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.model.GameSummary
import javax.inject.Inject

class GetGamesByIdsUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(ids: IntArray): List<GameSummary>? {
        if (ids.isEmpty()) {
            return emptyList()
        }

        return repository.getGamesByIds(ids)
    }

}