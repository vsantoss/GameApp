package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class CompanyDetail(
    val id: Int,
    val name: String,
    val createDate: String,
    val description: String,
    val developedGames: List<GameSummary>?,
    val publishedGames: List<GameSummary>?,
    val websites: List<Website>?
) : Serializable