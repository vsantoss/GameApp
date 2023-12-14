package com.vsanto.gameapp.ui.search.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.databinding.ItemRecentSearchBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemRecentSearchBinding.bind(view)

    fun bind(search: String, onItemSelected: (String) -> Unit) {
        binding.tvSearch.text = search

        binding.tvSearch.setOnClickListener { onItemSelected(search) }
    }

}