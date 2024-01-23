package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val listRepository: ListRepository,
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(): List<GameList> {
        val lists = listRepository.getLists().orEmpty()

        val allGameIds = lists.map { it.gamesIds }.flatten()
        val games = gameRepository.getGamesByIds(allGameIds.toIntArray()).orEmpty()

        lists.forEach { list ->
            val listGames = games.filter { list.gamesIds.contains(it.id) }
            list.games = listGames
        }

        return lists
    }

}