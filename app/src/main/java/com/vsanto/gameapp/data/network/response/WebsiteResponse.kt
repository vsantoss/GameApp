package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.vsanto.gameapp.domain.model.Website
import com.vsanto.gameapp.domain.model.WebsiteInfo

data class WebsiteResponse(
    @SerializedName("category") val category: Int,
    @SerializedName("url") val url: String
) {

    fun toDomain(): Website {
        return Website(
            info = WebsiteInfo.find(category)!!,
            url = url
        )
    }

}
