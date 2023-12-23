package com.vsanto.gameapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.model.RecentSearch
import com.vsanto.gameapp.domain.usecase.recentsearch.AddRecentSearchUseCase
import com.vsanto.gameapp.domain.usecase.recentsearch.GetRecentSearchesUseCase
import com.vsanto.gameapp.domain.usecase.recentsearch.RemoveRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val removeRemoveRecentSearchUseCase: RemoveRecentSearchUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<RecentSearchState>(RecentSearchState.Init)
    val state: StateFlow<RecentSearchState> = _state

    fun getRecentSearches() {
        viewModelScope.launch {
            _state.value = RecentSearchState.Loading
            val result = withContext(Dispatchers.IO) { getRecentSearchesUseCase() }

            if (result != null) {
                _state.value = RecentSearchState.Success(result)
            } else {
                _state.value = RecentSearchState.Error("Ha ocurrido un error")
            }
        }
    }

    fun addRecentSearch(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { addRecentSearchUseCase(RecentSearch(0, query)) }
        }
    }

}