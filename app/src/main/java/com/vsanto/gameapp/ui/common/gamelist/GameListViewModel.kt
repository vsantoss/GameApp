package com.vsanto.gameapp.ui.common.gamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.GetGamesByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val getGamesByIdsUseCase: GetGamesByIdsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<GameListState>(GameListState.Loading)
    val state: StateFlow<GameListState> = _state

    fun getGames(ids: IntArray) {
        viewModelScope.launch {
            _state.value = GameListState.Loading

            val result = withContext(Dispatchers.IO) { getGamesByIdsUseCase(ids) }
            if (result != null) {
                _state.value = GameListState.Success(result)
            } else {
                _state.value = GameListState.Error("Ha ocurrido un error")
            }
        }
    }

}