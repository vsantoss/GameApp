package com.vsanto.gameapp.data.network.response.igdb.game

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.domain.model.SimilarGame

data class SimilarGameResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val cover: ImageResponse?
) {
    fun toDomain(): SimilarGame {
        return SimilarGame(
            id = id,
            name = name,
            cover = cover?.toDomain(ImageSize.BIG_LOGO)
        )
    }
}
