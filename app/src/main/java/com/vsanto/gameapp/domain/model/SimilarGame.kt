package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class SimilarGame(
    val id: Int,
    val name: String,
    val cover: Image?
) : Serializable