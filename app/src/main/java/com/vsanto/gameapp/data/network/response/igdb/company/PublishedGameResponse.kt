package com.vsanto.gameapp.data.network.response.igdb.company

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.data.network.response.igdb.common.toDate
import com.vsanto.gameapp.domain.model.GameSummary

data class PublishedGameResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: Int,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("cover") val cover: ImageResponse?
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