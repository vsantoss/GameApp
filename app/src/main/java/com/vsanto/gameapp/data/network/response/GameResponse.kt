package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.Game
import java.text.SimpleDateFormat
import java.util.Locale

data class GameResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("cover") val cover: ImageResponse?
) {
    fun toDomain(): Game {
        return Game(
            id = id,
            name = name,
            releaseDate = getDateString(releaseDate),
            cover = cover?.toDomain()
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