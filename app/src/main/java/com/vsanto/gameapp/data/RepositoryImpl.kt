package com.vsanto.gameapp.data

import android.util.Log
import com.vsanto.gameapp.data.network.IGDBApiService
import com.vsanto.gameapp.domain.Repository
import com.vsanto.gameapp.domain.model.Game
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val igdbApiService: IGDBApiService) : Repository {

    override suspend fun searchGames(name: String): List<Game>? {
        runCatching { igdbApiService.searchGames(buildSearchGamesBody(name)) }
            .onSuccess { return it.map { gameResponse -> gameResponse.toDomain() } }
            .onFailure { Log.e("game", "Ha ocurrido el error ${it.message}") }

        return null
    }

    private fun buildSearchGamesBody(name: String): RequestBody {
        val queryBuilder = StringBuilder()
        queryBuilder.append("search \"$name\";")
        queryBuilder.append(
            "fields " +
                    "id, " +
                    "name, " +
                    "first_release_date, " +
                    "total_rating, " +
                    "summary, " +
                    "involved_companies.*, " +
                    "involved_companies.company.*, " +
                    "involved_companies.company.logo.url, " +
                    "cover.url, " +
                    "artworks.url, " +
                    "screenshots.url, " +
                    "themes.name, " +
                    "genres.name, " +
                    "game_modes.name, " +
                    "player_perspectives.name, " +
                    "platforms.abbreviation, " +
                    "similar_games.name, similar_games.cover.url;"
        )
        queryBuilder.append("where category = 0;")
        queryBuilder.append("limit 100;")

        return queryBuilder.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    }

}