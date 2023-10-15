package com.vsanto.gameapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemGameBinding
import com.vsanto.gameapp.domain.model.Game

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGameBinding.bind(view)

    fun bind(game: Game, onItemSelected: (Game) -> Unit) {
        binding.tvName.text = game.name
        binding.tvReleaseDate.text = game.releaseDate
        loadLogo(game)

        binding.root.setOnClickListener { onItemSelected(game) }
    }

    private fun loadLogo(game: Game) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            var url: String = "https:" + game.cover.url
            if(url.contains("t_thumb")){
                // Get big logo
                url = url.replace("t_thumb", "t_cover_big")
            }
            Picasso.get().load(url).into(binding.ivLogo)
        }
    }

}