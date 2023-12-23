package com.vsanto.gameapp.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameSummary

class GameResultAdapter(
    private var games: List<GameSummary> = emptyList(),
    private val onItemSelected: (GameSummary) -> Unit
) : RecyclerView.Adapter<GameResultViewHolder>() {

    fun updateList(list: List<GameSummary>) {
        this.games = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        return GameResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(games[position], onItemSelected)
    }

}