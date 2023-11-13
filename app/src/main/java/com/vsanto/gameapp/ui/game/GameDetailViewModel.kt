package com.vsanto.gameapp.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.GetGameByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val getGameByIdUseCase: GetGameByIdUseCase) :
    ViewModel() {

    private var _state = MutableStateFlow<GameDetailState>(GameDetailState.Loading)
    val state: StateFlow<GameDetailState> = _state

    fun getGame(id: Int) {
        viewModelScope.launch {
            _state.value = GameDetailState.Loading
            val result = withContext(Dispatchers.IO) { getGameByIdUseCase(id) }

            if (result != null) {
                _state.value = GameDetailState.Success(result)
            } else {
                _state.value = GameDetailState.Error("Ha ocurrido un error")
            }
        }
    }

}