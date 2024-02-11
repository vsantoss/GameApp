package com.vsanto.gameapp.motherobject

import com.vsanto.gameapp.data.network.response.igdb.common.ImageResponse
import com.vsanto.gameapp.data.network.response.igdb.common.WebsiteResponse
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.domain.model.UserGame
import com.vsanto.gameapp.domain.model.UserGameState

object GameMotherObject {

    //IGDB Responses
    val imageResponse =
        ImageResponse("//images.igdb.com/igdb/image/upload/t_thumb/wiqrk8nif7zntocaj9vf.jpg")

    val websiteResponse =
        WebsiteResponse(6, "https://www.twitch.tv/directory/game/The%20Last%20of%20Us")

    val timestampResponse: Long = 1371168000

    //Models
    val userGame = UserGame(1, UserGameState.PLAYED)
    val gameList =
        GameList(1, "Pendientes 2023", "Mis juegos pendientes de 2023", emptyList(), emptyList())

}