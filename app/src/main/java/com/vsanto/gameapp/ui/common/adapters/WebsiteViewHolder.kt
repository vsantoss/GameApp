package com.vsanto.gameapp.ui.common.adapters

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.databinding.ItemWebsiteBinding
import com.vsanto.gameapp.domain.model.Website

class WebsiteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemWebsiteBinding.bind(view)

    fun bind(website: Website) {
        val info = website.info
        binding.ivWebsiteLogo.setImageResource(info.img)
        binding.tvWebsiteName.text = binding.tvWebsiteName.context.getString(info.key)

        binding.root.setOnClickListener() {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(website.url)
            )
            binding.root.context.startActivity(intent)
        }
    }

}