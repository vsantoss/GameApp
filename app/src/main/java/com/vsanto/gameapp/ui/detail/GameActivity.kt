package com.vsanto.gameapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ActivityGameBinding
import com.vsanto.gameapp.domain.model.Game
import com.vsanto.gameapp.domain.model.Image
import com.vsanto.gameapp.domain.model.InvolvedCompany
import com.vsanto.gameapp.domain.model.SimilarGame
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GAME = "extra_game"
    }

    private lateinit var binding: ActivityGameBinding
    private lateinit var screenshotAdapter: ScreenshotAdapter
    private lateinit var similarGameAdapter: SimilarGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game: Game = intent.getSerializableExtra(EXTRA_GAME) as Game

        loadLogo(game)

        binding.tvName.text = game.name

        val developer: InvolvedCompany? = getDeveloperCompany(game)
        binding.tvDeveloper.text = developer?.name.orEmpty()
        binding.tvReleaseDate.text = game.releaseDate

        binding.tvSummary.text = game.summary
        binding.tvGenres.text = toString(game.genres)
        binding.tvThemes.text = toString(game.themes)
        binding.tvGameModes.text = toString(game.modes)
        binding.tvPlayerPerspectives.text = toString(game.playerPerspectives)
        binding.tvPlatforms.text = toString(game.platforms)

        initScreenshots(game.screenshots)
        initSimilarGames(game.similarGames)
    }

    private fun initScreenshots(screenshots: List<Image>?) {
        screenshotAdapter = ScreenshotAdapter(screenshots.orEmpty())
        binding.rvScreenshots.setHasFixedSize(true)
        binding.rvScreenshots.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvScreenshots.adapter = screenshotAdapter
    }

    private fun initSimilarGames(similarGames: List<SimilarGame>?) {
        similarGameAdapter = SimilarGameAdapter(similarGames.orEmpty())
        binding.rvSimilarGames.setHasFixedSize(true)
        binding.rvSimilarGames.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSimilarGames.adapter = similarGameAdapter
    }

    private fun getDeveloperCompany(game: Game): InvolvedCompany? {
        return game.involvedCompanies?.firstOrNull { it.developer }
    }

    private fun loadLogo(game: Game) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            var url: String = "https:" + game.cover.url
            if (url.contains("t_thumb")) {
                // Get big logo
                url = url.replace("t_thumb", "t_cover_big")
            }
            Picasso.get().load(url).into(binding.ivLogo)
        }
    }

    private fun toString(list: List<String>?): String {
        if (list.isNullOrEmpty()) {
            return "-"
        }
        return list.joinToString(separator = ", ")
    }

}