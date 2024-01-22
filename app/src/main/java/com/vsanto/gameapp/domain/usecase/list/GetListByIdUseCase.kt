package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class GetListByIdUseCase @Inject constructor(
    private val listRepository: ListRepository,
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(id: Int): GameList? {
        val list = listRepository.getList(id)
        list?.games = gameRepository.getGamesByIds(list?.gamesIds.orEmpty().toIntArray()).orEmpty()

        return list
    }

}