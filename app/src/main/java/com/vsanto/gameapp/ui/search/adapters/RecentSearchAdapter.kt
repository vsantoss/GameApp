package com.vsanto.gameapp.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.RecentSearch

class RecentSearchAdapter(
    private var searches: MutableList<RecentSearch> = mutableListOf(),
    private val onItemSelected: (RecentSearch) -> Unit,
    private val onItemLongSelected: (RecentSearch) -> Boolean
) : RecyclerView.Adapter<RecentSearchViewHolder>() {

    fun updateList(list: MutableList<RecentSearch>) {
        this.searches = list
        notifyDataSetChanged()
    }

    fun removeSearch(recentSearch: RecentSearch) {
        if (this.searches.remove(recentSearch)) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        return RecentSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_search, parent, false)
        )
    }

    override fun getItemCount() = searches.size

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(searches[position], onItemSelected, onItemLongSelected)
    }

}