package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import javax.inject.Inject

class GetCompanyByIdUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(id: Int) = repository.getCompanyById(id)

}