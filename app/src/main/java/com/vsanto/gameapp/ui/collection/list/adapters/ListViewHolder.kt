package com.vsanto.gameapp.ui.collection.list.adapters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.ItemListBinding
import com.vsanto.gameapp.domain.model.GameList

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListBinding.bind(view)

    private lateinit var gamesAdapter: SummaryListGamesAdapter

    fun bind(
        list: GameList,
        onItemSelected: (GameList) -> Unit,
        onItemLongSelected: (GameList) -> Boolean
    ) {
        binding.tvTitle.text = list.title
        val gamesSize = list.games.size
        binding.tvSize.text = binding.root.context.getString(R.string.list_games, gamesSize)

        initGames(list, onItemSelected, onItemLongSelected)

        binding.root.apply {
            setOnClickListener { onItemSelected(list) }
            setOnLongClickListener { onItemLongSelected(list) }
        }

    }

    private fun initGames(
        list: GameList,
        onItemSelected: (GameList) -> Unit,
        onItemLongSelected: (GameList) -> Boolean
    ) {
        gamesAdapter = SummaryListGamesAdapter(list, onItemSelected, onItemLongSelected)
        binding.rvGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = gamesAdapter
        }
    }

}