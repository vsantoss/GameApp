package com.vsanto.gameapp.data.network.response.igdb.common

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.Image

data class ImageResponse(
    @SerializedName("url") val url: String,
) {
    fun toDomain(size: ImageSize): Image {
        return Image(
            url = "https:${url
                .replace(ImageSize.DEFAULT.value, size.value)
                .replace(".jpg", ".png")
            }"
        )
    }
}