package com.vsanto.gameapp.ui.search.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.databinding.ItemRecentSearchBinding
import com.vsanto.gameapp.domain.model.RecentSearch

class RecentSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemRecentSearchBinding.bind(view)

    fun bind(
        recentSearch: RecentSearch,
        onItemSelected: (RecentSearch) -> Unit,
        onItemLongSelected: (RecentSearch) -> Boolean
    ) {
        binding.tvSearch.text = recentSearch.query

        binding.tvSearch.setOnClickListener { onItemSelected(recentSearch) }
        binding.tvSearch.setOnLongClickListener { onItemLongSelected(recentSearch) }
    }

}