package com.vsanto.gameapp.data.network.response.igdb.game

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.data.network.response.igdb.common.WebsiteResponse
import com.vsanto.gameapp.domain.model.GameDetail
import java.text.SimpleDateFormat
import java.util.Locale

data class GameDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("total_rating") val rating: Double?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("involved_companies") val involvedCompanies: List<InvolvedCompanyResponse>?,
    @SerializedName("cover") val cover: ImageResponse?,
    @SerializedName("screenshots") val screenshots: List<ImageResponse>?,
    @SerializedName("artworks") val artworks: List<ImageResponse>?,
    @SerializedName("genres") val genres: List<GenreResponse>?,
    @SerializedName("themes") val themes: List<ThemeResponse>?,
    @SerializedName("game_modes") val modes: List<GameModeResponse>?,
    @SerializedName("player_perspectives") val playerPerspectives: List<PlayerPerspectiveResponse>?,
    @SerializedName("platforms") val platforms: List<PlatformResponse>?,
    @SerializedName("similar_games") val similarGames: List<SimilarGameResponse>?,
    @SerializedName("websites") val websites: List<WebsiteResponse>?,
) {
    fun toDomain(): GameDetail {
        return GameDetail(id = id,
            name = name,
            releaseDate = getDateString(releaseDate),
            rating = rating ?: 0.0,
            summary = summary,
            involvedCompanies = involvedCompanies?.map { it.toDomain() },
            cover = cover?.toDomain(ImageSize.BIG_LOGO),
            screenshots = screenshots?.map { it.toDomain(ImageSize.BIG) },
            artworks = artworks?.map { it.toDomain(ImageSize.BIG) },
            genres = genres?.map { it.name },
            themes = themes?.map { it.name },
            modes = modes?.map { it.name },
            playerPerspectives = playerPerspectives?.map { it.name },
            platforms = platforms?.sortedBy { it.abbreviation }?.map { it.abbreviation },
            similarGames = similarGames?.map { it.toDomain() },
            websites = websites?.sortedBy { it.category }?.map { it.toDomain() })
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