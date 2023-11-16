package com.vsanto.gameapp.data.network.response.igdb.common

import java.util.Date

fun toDate(timestamp: Long?): Date? {

    return if (timestamp != null) {
        Date(timestamp * 1000)
    } else {
        null
    }
}