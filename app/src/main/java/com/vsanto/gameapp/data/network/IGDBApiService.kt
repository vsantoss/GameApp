package com.vsanto.gameapp.data.network

import com.vsanto.gameapp.data.network.response.GameResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface IGDBApiService {

    @POST("games")
    suspend fun searchGames(@Body requestBody: RequestBody): List<GameResponse>

}