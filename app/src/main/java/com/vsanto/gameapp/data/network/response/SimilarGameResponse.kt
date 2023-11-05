package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
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
