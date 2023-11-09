package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.Repository
import javax.inject.Inject

class GetGameByIdUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: Int) = repository.getGameById(id)

}