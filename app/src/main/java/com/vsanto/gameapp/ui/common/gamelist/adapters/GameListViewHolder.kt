package com.vsanto.gameapp.ui.common.gamelist.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemListGameBinding
import com.vsanto.gameapp.domain.model.GameSummary

class GameListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListGameBinding.bind(view)

    fun bind(
        game: GameSummary,
        onItemSelected: (GameSummary) -> Unit,
        onItemLongSelected: (GameSummary) -> Boolean
    ) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }

        binding.root.apply {
            setOnClickListener { onItemSelected(game) }
            setOnLongClickListener { onItemLongSelected(game) }
        }
    }

}