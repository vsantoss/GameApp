package com.vsanto.gameapp.data.network.response.igdb.company

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.data.network.response.igdb.common.WebsiteResponse
import com.vsanto.gameapp.data.network.response.igdb.common.toDate
import com.vsanto.gameapp.domain.model.CompanyDetail

data class CompanyDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: ImageResponse?,
    @SerializedName("description") val description: String?,
    @SerializedName("start_date") val createDate: Long?,
    @SerializedName("developed") val developedGames: List<DevelopedGameResponse>?,
    @SerializedName("published") val publishedGames: List<PublishedGameResponse>?,
    @SerializedName("websites") val websites: List<WebsiteResponse>?
) {
    fun toDomain(): CompanyDetail {
        return CompanyDetail(
            id = id,
            name = name,
            logo = logo?.toDomain(ImageSize.MED_LOGO),
            createDate = toDate(createDate),
            description = description.orEmpty(),
            developedGames = developedGames?.filter { it.category == 0 }?.map { it.toDomain() },
            publishedGames = publishedGames?.filter { it.category == 0 }?.map { it.toDomain() },
            websites = websites?.sortedBy { it.category }?.map { it.toDomain() }
        )
    }
}