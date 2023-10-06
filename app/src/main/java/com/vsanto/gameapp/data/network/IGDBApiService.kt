package com.vsanto.gameapp.data.network

import com.vsanto.gameapp.data.network.response.GameResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IGDBApiService {

    @POST("games")
    suspend fun getGames(@Body requestBody: RequestBody): Response<List<GameResponse>>

}