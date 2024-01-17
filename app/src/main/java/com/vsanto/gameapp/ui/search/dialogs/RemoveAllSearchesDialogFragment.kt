package com.vsanto.gameapp.ui.search.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.vsanto.gameapp.R

class RemoveAllSearchesDialogFragment(
    private val onCancelSelected: () -> Unit,
    private val onRemoveSelected: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it, R.style.DialogTheme)
                .setTitle(R.string.remove_all_searches_title)
                .setPositiveButton(R.string.remove_search_positive) { _, _ -> onRemoveSelected() }
                .setNegativeButton(R.string.remove_search_negative) { _, _ -> onCancelSelected() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}