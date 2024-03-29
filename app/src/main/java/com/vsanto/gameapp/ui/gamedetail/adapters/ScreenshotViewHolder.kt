package com.vsanto.gameapp.ui.gamedetail.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemScreenshotBinding
import com.vsanto.gameapp.domain.model.Image

class ScreenshotViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemScreenshotBinding.bind(view)

    fun bind(image: Image, onItemSelected: (String) -> Unit) {
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(image.url).into(binding.ivScreenshot)

        binding.root.setOnClickListener { onItemSelected(image.url) }
    }

}