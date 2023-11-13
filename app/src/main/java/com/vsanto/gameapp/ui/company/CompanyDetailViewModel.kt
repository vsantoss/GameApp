package com.vsanto.gameapp.ui.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsanto.gameapp.domain.usecase.GetCompanyByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel @Inject constructor(private val getCompanyByIdUseCase: GetCompanyByIdUseCase) :
    ViewModel() {

    private var _state = MutableStateFlow<CompanyDetailState>(CompanyDetailState.Loading)
    val state: StateFlow<CompanyDetailState> = _state

    fun getCompany(id: Int) {
        viewModelScope.launch {
            _state.value = CompanyDetailState.Loading
            val result = withContext(Dispatchers.IO) { getCompanyByIdUseCase(id) }

            if (result != null) {
                _state.value = CompanyDetailState.Success(result)
            } else {
                _state.value = CompanyDetailState.Error("Ha ocurrido un error")
            }
        }
    }

}