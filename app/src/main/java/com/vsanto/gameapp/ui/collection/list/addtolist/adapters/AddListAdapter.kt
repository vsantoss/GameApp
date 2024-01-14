package com.vsanto.gameapp.ui.collection.list.addtolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.GameList

class AddListAdapter(
    private var lists: List<GameList> = listOf(),
    private val onItemSelected: (GameList) -> Unit,
) : RecyclerView.Adapter<AddListViewHolder>() {

    fun updateList(lists: List<GameList>) {
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddListViewHolder {
        return AddListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_to_list, parent, false)
        )
    }

    override fun getItemCount() = lists.size

    override fun onBindViewHolder(holder: AddListViewHolder, position: Int) {
        holder.bind(lists[position], onItemSelected)
    }

}