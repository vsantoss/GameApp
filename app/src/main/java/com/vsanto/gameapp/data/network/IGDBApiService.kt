package com.vsanto.gameapp.data.network

import com.vsanto.gameapp.data.network.response.GameDetailResponse
import com.vsanto.gameapp.data.network.response.GameSummaryResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface IGDBApiService {

    @POST("games")
    suspend fun searchGames(@Body requestBody: RequestBody): List<GameSummaryResponse>

    @POST("games")
    suspend fun getGameById(@Body requestBody: RequestBody): List<GameDetailResponse>

}