package com.vsanto.gameapp.ui.company

import com.vsanto.gameapp.domain.model.CompanyDetail

sealed class CompanyDetailState {

    data object Loading : CompanyDetailState()
    data class Error(val error: String) : CompanyDetailState()
    data class Success(val company: CompanyDetail) : CompanyDetailState()

}