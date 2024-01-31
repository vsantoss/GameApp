package com.vsanto.gameapp.ui.collection.list.addtolist.adapters

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.ItemAddToListBinding
import com.vsanto.gameapp.domain.model.GameList

class AddListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemAddToListBinding.bind(view)

    fun bind(
        list: GameList,
        isBlockedList: Boolean,
        onItemSelected: (GameList) -> Unit,
    ) {
        binding.tvTitle.text = list.title

        val gamesSize = list.games.size
        binding.tvSize.text = binding.root.context.getString(R.string.list_games, gamesSize)

        if (isBlockedList) {
            binding.ivBlocked.isVisible = true
        }

        binding.root.setOnClickListener {
            if (!isBlockedList) {
                binding.ivSelected.isVisible = !binding.ivSelected.isVisible
                onItemSelected(list)
            } else {
                val shakeAnimation: Animation =
                    AnimationUtils.loadAnimation(binding.root.context, R.anim.shake)
                binding.ivBlocked.startAnimation(shakeAnimation)
            }
        }
    }

}