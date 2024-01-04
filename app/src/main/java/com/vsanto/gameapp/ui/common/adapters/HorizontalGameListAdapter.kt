package com.vsanto.gameapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameSummary

class HorizontalGameListAdapter(
    private var games: List<GameSummary> = emptyList(),
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<HorizontalGameListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalGameListViewHolder {
        return HorizontalGameListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_horizontal_list_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: HorizontalGameListViewHolder, position: Int) {
        holder.bind(games[position], onItemSelected)
    }

}