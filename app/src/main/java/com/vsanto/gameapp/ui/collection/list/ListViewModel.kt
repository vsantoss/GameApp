package com.vsanto.gameapp.ui.collection.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.domain.usecase.list.AddListUseCase
import com.vsanto.gameapp.domain.usecase.list.GetListsUseCase
import com.vsanto.gameapp.domain.usecase.list.RemoveListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val addListUseCase: AddListUseCase,
    private val removeListUseCase: RemoveListUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<ListState>(ListState.Loading)
    val state: StateFlow<ListState> = _state

    private var _newState = MutableStateFlow<NewListState>(NewListState.Init)
    val newState: StateFlow<NewListState> = _newState

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

    fun addList(title: String) {
        viewModelScope.launch {
            _newState.value = NewListState.Loading

            val idInserted: Int =
                withContext(Dispatchers.IO) { addListUseCase(GameList(0, title, emptyList())) }

            _newState.value = NewListState.Success(idInserted)
        }
    }

    fun removeList(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { removeListUseCase(id) }
        }
    }

}