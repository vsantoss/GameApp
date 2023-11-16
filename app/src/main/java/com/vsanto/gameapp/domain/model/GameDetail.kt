package com.vsanto.gameapp.domain.model

import java.io.Serializable
import java.util.Date

data class GameDetail(
    val id: Int,
    val name: String,
    val releaseDate: Date?,
    val rating: Double,
    val summary: String?,
    val involvedCompanies: List<InvolvedCompany>?,
    val cover: Image?,
    val screenshots: List<Image>?,
    val artworks: List<Image>?,
    val genres: List<String>?,
    val themes: List<String>?,
    val modes: List<String>?,
    val playerPerspectives: List<String>?,
    val platforms: List<String>?,
    val similarGames: List<SimilarGame>?,
    val websites: List<Website>?
) :
    Serializable
