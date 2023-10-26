package com.vsanto.gameapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsanto.gameapp.data.network.IGDBApiService
import com.vsanto.gameapp.data.network.response.GameResponse
import com.vsanto.gameapp.databinding.ActivityMainBinding
import com.vsanto.gameapp.domain.model.Game
import com.vsanto.gameapp.ui.detail.GameActivity
import com.vsanto.gameapp.ui.detail.GameActivity.Companion.EXTRA_GAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = getRetrofit()

        initListeners()
        initUI()
    }

    private fun initListeners() {
        binding.svGame.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initUI() {
        adapter = GameAdapter { navigateToDetail(it) }
        binding.rvGames.setHasFixedSize(true)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody: RequestBody =
                getSearchBody(query).toRequestBody("text/plain".toMediaTypeOrNull())

            val response: Response<List<GameResponse>> =
                retrofit.create(IGDBApiService::class.java).getGames(requestBody)

            if (response.isSuccessful) {
                Log.i("rest", "Peticion correcta")
                val games: List<GameResponse>? = response.body()
                if (games != null) {
                    runOnUiThread {
                        adapter.updateList(games.map { gameResponse -> gameResponse.toDomain() })
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun navigateToDetail(game: Game) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(EXTRA_GAME, game)
        startActivity(intent)
    }

    private fun getSearchBody(name: String): String {
        val queryBuilder = StringBuilder()
        queryBuilder.append("search \"$name\";")
        queryBuilder.append(
            "fields " +
                    "id, " +
                    "name, " +
                    "first_release_date, " +
                    "summary, " +
                    "involved_companies.*, " +
                    "involved_companies.company.*, " +
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
        return queryBuilder.toString()
    }

    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Client-ID", "8kjgodb5ozfyj3q70rmekr9lioe59z")
                .addHeader("Authorization", "Bearer v3s917uilrqwh5v1b3aj8cgw85y7tl")
                .addHeader("Content-Type", "text/plain")
                .build()
            chain.proceed(request)
        }

        httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )

        return Retrofit.Builder()
            .baseUrl("https://api.igdb.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }
}