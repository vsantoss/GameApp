package com.vsanto.gameapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.domain.model.Image

class ScreenshotAdapter(
    private var screenshots: List<Image> = emptyList()
) : RecyclerView.Adapter<ScreenshotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        return ScreenshotViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_screenshot, parent, false)
        )
    }

    override fun getItemCount() = screenshots.size

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        holder.bind(screenshots[position])
    }

}