package com.vsanto.gameapp.ui.collection.list.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.list.GetListByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val getListByIdUseCase: GetListByIdUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<ListDetailState>(ListDetailState.Loading)
    val state: StateFlow<ListDetailState> = _state

    fun getList(id: Int) {
        viewModelScope.launch {
            _state.value = ListDetailState.Loading

            val result = withContext(Dispatchers.IO) { getListByIdUseCase(id) }

            if (result != null) {
                _state.value = ListDetailState.Success(result)
            } else {
                _state.value = ListDetailState.Error("Ha ocurrido un error")
            }
        }
    }

}