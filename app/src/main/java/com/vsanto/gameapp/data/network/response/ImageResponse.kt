package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.Image

data class ImageResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
) {
    fun toDomain(): Image {
        return Image(
            id = id,
            url = url
        )
    }
}