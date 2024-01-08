package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.ListRepository
import com.vsanto.gameapp.domain.model.GameList
import javax.inject.Inject

class GetListByIdUseCase @Inject constructor(private val repository: ListRepository) {

    suspend operator fun invoke(id: Int): GameList? {
        return repository.getList(id)
    }

}