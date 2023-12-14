package com.vsanto.gameapp.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameSummary

class GameAdapter(
    private var games: List<GameSummary> = emptyList(),
    private val onItemSelected: (GameSummary) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {

    fun updateList(list: List<GameSummary>) {
        this.games = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position], onItemSelected)
    }

}