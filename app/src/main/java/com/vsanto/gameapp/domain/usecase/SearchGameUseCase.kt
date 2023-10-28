package com.vsanto.gameapp.domain.usecase

import com.vsanto.gameapp.domain.Repository
import javax.inject.Inject

class SearchGameUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String) = repository.searchGames(name)

}