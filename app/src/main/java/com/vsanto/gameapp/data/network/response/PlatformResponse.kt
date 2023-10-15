package com.vsanto.gameapp.data.network.response

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @SerializedName("abbreviation") val abbreviation: String
)
