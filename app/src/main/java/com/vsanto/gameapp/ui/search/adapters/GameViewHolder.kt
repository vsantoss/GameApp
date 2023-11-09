package com.vsanto.gameapp.ui.search.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemGameBinding
import com.vsanto.gameapp.domain.model.GameSummary

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGameBinding.bind(view)

    fun bind(game: GameSummary, onItemSelected: (GameSummary) -> Unit) {
        binding.tvName.text = game.name
        binding.tvReleaseDate.text = game.releaseDate
        loadLogo(game)

        binding.root.setOnClickListener { onItemSelected(game) }
    }

    private fun loadLogo(game: GameSummary) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }
    }

}