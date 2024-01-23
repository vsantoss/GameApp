package com.vsanto.gameapp.ui.collection.list.detail.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.vsanto.gameapp.R

class RemoveGameFromListDialogFragment(
    private val gameName: String,
    private val onCancelSelected: () -> Unit,
    private val onRemoveSelected: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it, R.style.DialogTheme)
                .setTitle(R.string.remove_game_from_list_title)
                .setMessage(gameName)
                .setPositiveButton(R.string.remove_game_from_list_positive) { _, _ -> onRemoveSelected() }
                .setNegativeButton(R.string.remove_game_from_list_negative) { _, _ -> onCancelSelected() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}