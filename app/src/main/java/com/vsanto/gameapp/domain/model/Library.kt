package com.vsanto.gameapp.domain.model

data class Library(
    val games: List<UserGame>,
    val lists: List<GameList>
)