package com.vsanto.gameapp.domain.model

import java.io.Serializable

data class InvolvedCompany(
    val name: String,
    val logo: Image?,
    val developer: Boolean,
    val publisher: Boolean,
    val porting: Boolean,
    val supporting: Boolean
) : Serializable
