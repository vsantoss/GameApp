package com.vsanto.gameapp.data.network.response.igdb.game

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.data.network.response.igdb.common.toDate
import com.vsanto.gameapp.domain.model.GameSummary

class GameSummaryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("cover") val cover: ImageResponse?,
) {

    fun toDomain(): GameSummary {
        return GameSummary(
            id = id,
            name = name,
            releaseDate = toDate(releaseDate),
            cover = cover?.toDomain(ImageSize.BIG_LOGO)
        )
    }

}