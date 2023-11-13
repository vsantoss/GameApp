package com.vsanto.gameapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.Website

class WebsiteAdapter(private var websites: List<Website> = emptyList()) :
    RecyclerView.Adapter<WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        return WebsiteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_website, parent, false)
        )
    }

    override fun getItemCount(): Int = websites.size

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        holder.bind(websites[position])
    }

}