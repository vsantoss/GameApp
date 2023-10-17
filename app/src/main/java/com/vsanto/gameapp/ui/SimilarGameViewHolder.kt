package com.vsanto.gameapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemSimilarGameBinding
import com.vsanto.gameapp.domain.model.SimilarGame

class SimilarGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSimilarGameBinding.bind(view)

    fun bind(similarGame: SimilarGame) {
        Picasso.get().isLoggingEnabled = true

        if (similarGame.cover != null) {
            var url: String = "https:" + similarGame.cover.url
            if (url.contains("t_thumb")) {
                // Get 1080p screenshot
                url = url.replace("t_thumb", "t_1080p")
            }
            Picasso.get().load(url).into(binding.ivLogo)
        }
    }

}