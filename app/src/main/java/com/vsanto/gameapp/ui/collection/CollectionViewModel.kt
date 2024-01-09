package com.vsanto.gameapp.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.GetCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<CollectionState>(CollectionState.Loading)
    val state: StateFlow<CollectionState> = _state

    fun getCollection() {
        viewModelScope.launch {
            _state.value = CollectionState.Loading

            val result = withContext(Dispatchers.IO) { getCollectionUseCase() }
            _state.value = CollectionState.Success(result)
        }
    }

}