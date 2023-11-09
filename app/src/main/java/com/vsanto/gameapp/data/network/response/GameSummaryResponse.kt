package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.GameSummary
import java.text.SimpleDateFormat
import java.util.Locale

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
            releaseDate = getDateString(releaseDate),
            cover = cover?.toDomain(ImageSize.BIG_LOGO)
        )
    }

    private fun getDateString(timestamp: Long?): String {
        return if (timestamp != null) {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            simpleDateFormat.format(timestamp * 1000L)
        } else {
            ""
        }
    }

}