package com.vsanto.gameapp.ui.collection.list.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.databinding.ItemListBinding
import com.vsanto.gameapp.domain.model.GameList

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListBinding.bind(view)

    fun bind(list: GameList) {
        binding.tvTitle.text = list.title
        binding.tvSize.text = list.games.size.toString()
    }
}