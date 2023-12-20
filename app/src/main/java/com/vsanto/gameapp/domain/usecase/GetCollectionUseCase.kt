package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke() = repository.getUserGames()

}