package com.vsanto.gameapp.ui.common.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemHorizontalListGameBinding
import com.vsanto.gameapp.domain.model.GameSummary

class HorizontalGameListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHorizontalListGameBinding.bind(view)

    fun bind(game: GameSummary, onItemSelected: (Int) -> Unit) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }

        binding.root.setOnClickListener { onItemSelected(game.id) }
    }

}