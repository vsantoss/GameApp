package com.vsanto.gameapp.domain.usecase.recentsearch

import com.vsanto.gameapp.domain.RecentSearchRepository
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(private val repository: RecentSearchRepository) {

    suspend operator fun invoke() = repository.getRecentSearches()

}