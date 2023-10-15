package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.InvolvedCompany

data class InvolvedCompanyResponse(
    @SerializedName("company") val company: CompanyResponse?,
    @SerializedName("developer") val developer: Boolean,
    @SerializedName("publisher") val publisher: Boolean,
    @SerializedName("porting") val porting: Boolean,
    @SerializedName("supporting") val supporting: Boolean
) {

    fun toDomain(): InvolvedCompany {
        return InvolvedCompany(
            name = company?.name.orEmpty(),
            developer = developer,
            publisher = publisher,
            porting = porting,
            supporting = supporting
        )
    }
}
