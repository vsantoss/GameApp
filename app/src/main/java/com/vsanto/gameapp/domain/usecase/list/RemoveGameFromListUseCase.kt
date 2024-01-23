package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.ListRepository
import javax.inject.Inject

class RemoveGameFromListUseCase @Inject constructor(private val repository: ListRepository) {

    suspend operator fun invoke(listId: Int, gameId: Int) =
        repository.removeGameFromList(listId, gameId)

}