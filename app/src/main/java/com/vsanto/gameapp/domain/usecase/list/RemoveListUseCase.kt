package com.vsanto.gameapp.domain.usecase.list

import com.vsanto.gameapp.domain.ListRepository
import javax.inject.Inject

class RemoveListUseCase @Inject constructor(private val repository: ListRepository) {

    suspend operator fun invoke(id: Int) = repository.removeList(id)

}