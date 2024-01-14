package com.vsanto.gameapp.ui.gamedetail.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.R

class ExpandedImageDialogFragment(
    private val src: String
) : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialog = Dialog(it)

            val view = layoutInflater.inflate(R.layout.dialog_expanded_image, null)
            val iv: ImageView = view.findViewById(R.id.ivImage)
            Picasso.get().load(src).into(iv)

            dialog.setContentView(view)
            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}