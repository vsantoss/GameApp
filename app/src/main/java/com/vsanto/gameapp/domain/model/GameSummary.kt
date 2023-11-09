package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class GameSummary(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val cover: Image?
) : Serializable