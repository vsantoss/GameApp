package com.vsanto.gameapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.SimilarGame

class SimilarGameAdapter(
    private var similarGames: List<SimilarGame> = emptyList()
) : RecyclerView.Adapter<SimilarGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarGameViewHolder {
        return SimilarGameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_similar_game, parent, false)
        )
    }

    override fun getItemCount() = similarGames.size

    override fun onBindViewHolder(holder: SimilarGameViewHolder, position: Int) {
        holder.bind(similarGames[position])
    }

}