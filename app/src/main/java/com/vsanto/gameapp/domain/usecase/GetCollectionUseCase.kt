package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.Library
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val gameRepository: GameRepository,
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(): Library {
        val games = gameRepository.getUserGames().orEmpty()
        val lists = listRepository.getLists().orEmpty()

        return Library(games, lists)
    }

}