package com.vsanto.gameapp.ui.gamedetail.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vsanto.gameapp.R

class UserGameDialogFragment(
    private val name: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialog = BottomSheetDialog(it)
            val view = layoutInflater.inflate(R.layout.dialog_user_game, null)

            val tvName: TextView = view.findViewById(R.id.tvName)
            tvName.text = name

            dialog.setContentView(view)
            dialog.create()
            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}