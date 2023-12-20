package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class RecentSearch (
    val id: Int,
    val query: String
) : Serializable