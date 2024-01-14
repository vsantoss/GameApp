package com.vsanto.gameapp.ui.collection.list.addtolist.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.ItemAddToListBinding
import com.vsanto.gameapp.domain.model.GameList

class AddListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemAddToListBinding.bind(view)

    fun bind(
        list: GameList,
        onItemSelected: (GameList) -> Unit,
    ) {
        binding.tvTitle.text = list.title

        val gamesSize = list.games.size
        binding.tvSize.text = binding.root.context.getString(R.string.list_games, gamesSize)

        binding.root.setOnClickListener {
            binding.ivSelected.isVisible = !binding.ivSelected.isVisible
            onItemSelected(list)
        }
    }

}