package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class Game(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val summary: String?,
    val cover: Image?,
    val screenshots: List<Image>?,
    val artworks: List<Image>?,
    val genres: List<String>?,
    val themes: List<String>?,
    val modes: List<String>?,
    val playerPerspectives: List<String>?
) :
    Serializable