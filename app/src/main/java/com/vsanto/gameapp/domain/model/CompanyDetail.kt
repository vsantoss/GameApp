package com.vsanto.gameapp.domain.model

import java.io.Serializable
import java.util.Date

data class CompanyDetail(
    val id: Int,
    val name: String,
    val logo: Image?,
    val createDate: Date?,
    val description: String,
    val developedGames: List<GameSummary>?,
    val publishedGames: List<GameSummary>?,
    val websites: List<Website>?
) : Serializable