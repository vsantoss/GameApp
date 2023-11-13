package com.vsanto.gameapp.ui.company.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameSummary

class ProducedGameAdapter(
    private var games: List<GameSummary> = emptyList(),
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<ProducedGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProducedGameViewHolder {
        return ProducedGameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_produced_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: ProducedGameViewHolder, position: Int) {
        holder.bind(games[position], onItemSelected)
    }

}