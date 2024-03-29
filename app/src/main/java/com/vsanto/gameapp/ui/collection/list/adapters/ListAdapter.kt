package com.vsanto.gameapp.ui.collection.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameList

class ListAdapter(
    private var lists: MutableList<GameList> = mutableListOf(),
    private val onItemSelected: (GameList) -> Unit,
    private val onItemLongSelected: (GameList) -> Boolean
) : RecyclerView.Adapter<ListViewHolder>() {

    fun updateList(list: MutableList<GameList>) {
        this.lists = list
        notifyDataSetChanged()
    }

    fun removeList(list: GameList) {
        if (this.lists.remove(list)) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount() = lists.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(lists[position], onItemSelected, onItemLongSelected)
    }

}