package com.vsanto.gameapp.ui.game.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemSimilarGameBinding
import com.vsanto.gameapp.domain.model.SimilarGame

class SimilarGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSimilarGameBinding.bind(view)

    fun bind(similarGame: SimilarGame, onItemSelected: (Int) -> Unit) {
        if (similarGame.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(similarGame.cover.url).into(binding.ivLogo)
        }

        binding.root.setOnClickListener { onItemSelected(similarGame.id) }
    }

}