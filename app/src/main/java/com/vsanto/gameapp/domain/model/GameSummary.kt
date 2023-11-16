package com.vsanto.gameapp.domain.model

import java.io.Serializable
import java.util.Date

data class GameSummary(
    val id: Int,
    val name: String,
    val releaseDate: Date?,
    val cover: Image?
) : Serializable