package com.vsanto.gameapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameSummary

class GameListAdapter(
    private var games: List<GameSummary> = emptyList(),
    private val onItemSelected: (GameSummary) -> Unit
) : RecyclerView.Adapter<GameListViewHolder>() {

    fun updateList(list: List<GameSummary>) {
        this.games = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        return GameListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        holder.bind(games[position], onItemSelected)
    }

}