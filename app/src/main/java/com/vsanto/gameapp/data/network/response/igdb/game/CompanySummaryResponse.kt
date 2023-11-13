package com.vsanto.gameapp.data.network.response.igdb.game

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse

data class CompanySummaryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: ImageResponse?
)
