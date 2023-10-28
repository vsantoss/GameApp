package com.vsanto.gameapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.SearchGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchGameUseCase: SearchGameUseCase) :
    ViewModel() {

    private var _state = MutableStateFlow<SearchState>(SearchState.Init)
    val state: StateFlow<SearchState> = _state

    fun searchGame(name: String) {
        viewModelScope.launch {
            _state.value = SearchState.Loading
            val result = withContext(Dispatchers.IO) { searchGameUseCase(name) }

            if (result != null) {
                _state.value = SearchState.Success(result)
            } else {
                _state.value = SearchState.Error("Ha ocurrido un error")
            }
        }
    }
}