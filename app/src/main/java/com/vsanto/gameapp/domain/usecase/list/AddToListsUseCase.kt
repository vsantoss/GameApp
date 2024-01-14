package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class AddToListsUseCase @Inject constructor(private val repository: ListRepository) {

    suspend operator fun invoke(gameId: Int, lists: List<GameList>) {

    }

}