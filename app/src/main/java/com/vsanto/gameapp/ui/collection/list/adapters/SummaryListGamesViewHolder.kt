package com.vsanto.gameapp.ui.collection.list.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemSummaryListGameBinding
import com.vsanto.gameapp.domain.model.GameSummary

class SummaryListGamesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSummaryListGameBinding.bind(view)

    fun bind(game: GameSummary) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }
    }

}