package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class AddListUseCase  @Inject constructor(private val repository: ListRepository) {

    suspend operator fun invoke(list: GameList) = repository.addList(list = list)

}