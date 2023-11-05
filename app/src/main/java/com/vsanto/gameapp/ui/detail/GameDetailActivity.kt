package com.vsanto.gameapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.ActivityGameDetailBinding
import com.vsanto.gameapp.domain.model.Game
import com.vsanto.gameapp.domain.model.Image
import com.vsanto.gameapp.domain.model.InvolvedCompany
import com.vsanto.gameapp.domain.model.SimilarGame
import com.vsanto.gameapp.ui.detail.adapters.CompanyAdapter
import com.vsanto.gameapp.ui.detail.adapters.ScreenshotAdapter
import com.vsanto.gameapp.ui.detail.adapters.SimilarGameAdapter

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding
    private val args: GameDetailActivityArgs by navArgs()

    private lateinit var screenshotAdapter: ScreenshotAdapter
    private lateinit var companyAdapter: CompanyAdapter
    private lateinit var similarGameAdapter: SimilarGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        val game: Game = args.game

        loadLogo(game)

        binding.tvName.text = game.name

        val developer: InvolvedCompany? = getDeveloperCompany(game)
        binding.tvDeveloper.text = developer?.name.orEmpty()
        binding.tvReleaseDate.text = game.releaseDate
        initRating(game.rating)

        binding.tvSummary.text = game.summary
        binding.tvGenres.text = toString(game.genres)
        binding.tvThemes.text = toString(game.themes)
        binding.tvGameModes.text = toString(game.modes)
        binding.tvPlayerPerspectives.text = toString(game.playerPerspectives)
        binding.tvPlatforms.text = toString(game.platforms)

        initScreenshots(game.screenshots)
        initInvolvedCompanies(game.involvedCompanies)
        initSimilarGames(game.similarGames)
    }

    private fun initRating(rating: Double) {
        val parsedRating = String.format("%.1f", rating / 10).toDouble()
        binding.tvRating.text = parsedRating.toString()

        when (parsedRating) {
            in 0.01..2.00 -> {
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.E_rating)
                )
            }

            in 2.01..4.00 -> {
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.D_rating)
                )
            }

            in 4.01..6.00 -> {
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.C_rating)
                )
            }

            in 6.01..8.00 -> {
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.B_rating)
                )
            }

            in 8.01..10.00 -> {
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.A_rating)
                )
            }

            else -> {
                binding.tvRating.text = "N/A"
                binding.cvRating.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.N_A_rating)
                )
            }
        }
    }

    private fun initScreenshots(screenshots: List<Image>?) {
        screenshotAdapter = ScreenshotAdapter(screenshots.orEmpty())
        binding.rvScreenshots.setHasFixedSize(true)
        binding.rvScreenshots.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvScreenshots.adapter = screenshotAdapter
    }

    private fun initInvolvedCompanies(involvedCompanies: List<InvolvedCompany>?) {
        companyAdapter = CompanyAdapter(involvedCompanies.orEmpty())
        binding.rvInvolvedCompanies.setHasFixedSize(true)
        binding.rvInvolvedCompanies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvInvolvedCompanies.adapter = companyAdapter
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
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }
    }

    private fun toString(list: List<String>?): String {
        if (list.isNullOrEmpty()) {
            return "-"
        }
        return list.joinToString(separator = ", ")
    }

}