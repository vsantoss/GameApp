package com.vsanto.gameapp.ui.collection.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.list.GetListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<ListState>(ListState.Loading)
    val state: StateFlow<ListState> = _state

    fun getLists() {
        viewModelScope.launch {
            _state.value = ListState.Loading

            val result = withContext(Dispatchers.IO) { getListsUseCase() }

            if (result != null) {
                _state.value = ListState.Success(result)
            } else {
                _state.value = ListState.Error("Ha ocurrido un error")
            }
        }

    }

}