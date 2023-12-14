package com.vsanto.gameapp.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R

class SearchAdapter(
    private var searches: MutableList<String> = mutableListOf(),
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<SearchViewHolder>() {

    fun add(query: String?) {
        if (query != null) {
            this.searches.add(0, query)
            notifyItemInserted(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_search, parent, false)
        )
    }

    override fun getItemCount() = searches.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searches[position], onItemSelected)
    }

}