package com.vsanto.gameapp.ui.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.vsanto.gameapp.R

class DiscardDialogFragment(
    private val onCancelSelected: () -> Unit,
    private val onDiscardSelected: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it, R.style.DialogTheme)
                .setTitle(R.string.discard_title)
                .setMessage(R.string.discard_message)
                .setPositiveButton(R.string.discard_positive) { _, _ -> onDiscardSelected() }
                .setNegativeButton(R.string.discard_negative) { _, _ -> onCancelSelected() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}