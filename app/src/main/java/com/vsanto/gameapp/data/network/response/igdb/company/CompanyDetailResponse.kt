package com.vsanto.gameapp.data.network.response.igdb.company

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.WebsiteResponse
import com.vsanto.gameapp.domain.model.CompanyDetail
import java.text.SimpleDateFormat
import java.util.Locale

data class CompanyDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("created_at") val createDate: Long?,
    @SerializedName("developed") val developedGames: List<DevelopedGameResponse>?,
    @SerializedName("published") val publishedGames: List<PublishedGameResponse>?,
    @SerializedName("websites") val websites: List<WebsiteResponse>?
) {
    fun toDomain(): CompanyDetail {
        return CompanyDetail(
            id = id,
            name = name,
            createDate = getDateString(createDate),
            description = description.orEmpty(),
            developedGames = developedGames?.map { it.toDomain() },
            publishedGames = publishedGames?.map { it.toDomain() },
            websites = websites?.sortedBy { it.category }?.map { it.toDomain() }
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