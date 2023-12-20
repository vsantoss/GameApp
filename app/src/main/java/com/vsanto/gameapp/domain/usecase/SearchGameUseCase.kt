package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.GameRepository
import javax.inject.Inject

class SearchGameUseCase @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(name: String) = repository.searchGames(name)

}