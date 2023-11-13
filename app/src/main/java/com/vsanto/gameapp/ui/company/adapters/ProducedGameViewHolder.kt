package com.vsanto.gameapp.ui.company.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemProducedGameBinding
import com.vsanto.gameapp.domain.model.GameSummary

class ProducedGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProducedGameBinding.bind(view)

    fun bind(gameSummary: GameSummary, onItemSelected: (Int) -> Unit) {
        if (gameSummary.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(gameSummary.cover.url).into(binding.ivLogo)
        }

        binding.root.setOnClickListener { onItemSelected(gameSummary.id) }
    }

}