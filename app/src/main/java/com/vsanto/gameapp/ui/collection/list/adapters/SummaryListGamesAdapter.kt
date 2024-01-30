package com.vsanto.gameapp.ui.collection.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameList

class SummaryListGamesAdapter(
    private val list: GameList,
    private val onItemSelected: (GameList) -> Unit,
    private val onItemLongSelected: (GameList) -> Boolean
) : RecyclerView.Adapter<SummaryListGamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryListGamesViewHolder {
        return SummaryListGamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_summary_list_game, parent, false)
        )
    }

    override fun getItemCount() = list.games.size

    override fun onBindViewHolder(holder: SummaryListGamesViewHolder, position: Int) {
        holder.bind(list, list.games[position], onItemSelected, onItemLongSelected)
    }

}