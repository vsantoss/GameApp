package com.vsanto.gameapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.databinding.ItemScreenshotBinding
import com.vsanto.gameapp.domain.model.Image

class ScreenshotViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemScreenshotBinding.bind(view)

    fun bind(image: Image){
        Picasso.get().isLoggingEnabled = true
        var url: String = "https:" + image.url
        if (url.contains("t_thumb")) {
            // Get 1080p screenshot
            url = url.replace("t_thumb", "t_1080p")
        }
        Picasso.get().load(url).into(binding.ivScreenshot)
    }

}