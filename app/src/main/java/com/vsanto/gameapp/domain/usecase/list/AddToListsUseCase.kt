package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.domain.model.UserGame
import com.vsanto.gameapp.domain.model.UserGameState
import javax.inject.Inject

class AddToListsUseCase @Inject constructor(
    private val listRepository: ListRepository,
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(gameId: Int, lists: List<GameList>) {
        if (!gameRepository.existsUserGame(gameId)) {
            gameRepository.addUserGame(UserGame(gameId, UserGameState.UNSELECTED))
        }

        listRepository.addGameToLists(gameId, lists.map { it.id })
    }

}