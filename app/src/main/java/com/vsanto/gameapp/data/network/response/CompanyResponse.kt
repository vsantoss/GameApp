package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName

data class CompanyResponse(
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: ImageResponse?
)
