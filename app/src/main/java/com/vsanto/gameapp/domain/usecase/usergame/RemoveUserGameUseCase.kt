package com.vsanto.gameapp.domain.usecase.usergame

import com.vsanto.gameapp.domain.GameRepository
import javax.inject.Inject

class RemoveUserGameUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(gameId: Int) = repository.removeUserGame(gameId)

}