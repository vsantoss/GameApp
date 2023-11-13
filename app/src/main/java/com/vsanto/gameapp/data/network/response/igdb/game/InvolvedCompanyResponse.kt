package com.vsanto.gameapp.data.network.response.igdb.game

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.data.network.response.igdb.common.ImageSize
import com.vsanto.gameapp.domain.model.InvolvedCompany

data class InvolvedCompanyResponse(
    @SerializedName("company") val company: CompanySummaryResponse?,
    @SerializedName("developer") val developer: Boolean,
    @SerializedName("publisher") val publisher: Boolean,
    @SerializedName("porting") val porting: Boolean,
    @SerializedName("supporting") val supporting: Boolean
) {

    fun toDomain(): InvolvedCompany {
        return InvolvedCompany(
            id = company?.id ?: 0,
            name = company?.name.orEmpty(),
            logo = company?.logo?.toDomain(ImageSize.MED_LOGO),
            developer = developer,
            publisher = publisher,
            porting = porting,
            supporting = supporting
        )
    }
}
