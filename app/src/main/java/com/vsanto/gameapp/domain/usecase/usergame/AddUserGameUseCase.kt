package com.vsanto.gameapp.domain.usecase.usergame

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.model.UserGame
import javax.inject.Inject

class AddUserGameUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(userGame: UserGame) = repository.addUserGame(userGame)

}