package com.vsanto.gameapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ActivityGameBinding
import com.vsanto.gameapp.domain.model.Game

class GameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GAME = "extra_game"
    }

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game: Game = intent.getSerializableExtra(EXTRA_GAME) as Game

        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load("https:" + game.cover.url).into(binding.ivLogo)
        }

        binding.tvName.text = game.name
        binding.tvReleaseDate.text = game.releaseDate

        binding.tvSummary.text = game.summary
        binding.tvGenres.text = toString(game.genres)
        binding.tvThemes.text = toString(game.themes)
        binding.tvGameModes.text = toString(game.modes)
        binding.tvPlayerPerspectives.text = toString(game.playerPerspectives)
    }

    private fun toString(list: List<String>?): String {
        if (list.isNullOrEmpty()) {
            return "-"
        }
        return list.joinToString(separator = ", ")
    }

}