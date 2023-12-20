package com.vsanto.gameapp.domain.usecase.recentsearch

import com.vsanto.gameapp.domain.RecentSearchRepository
import com.vsanto.gameapp.domain.model.RecentSearch
import javax.inject.Inject

class AddRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {

    suspend operator fun invoke(recentSearch: RecentSearch) =
        repository.addRecentSearch(recentSearch)

}