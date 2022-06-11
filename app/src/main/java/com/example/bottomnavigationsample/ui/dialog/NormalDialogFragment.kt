package com.example.bottomnavigationsample.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NormalDialogFragment(
    private val message: String,
    private val onPositiveButton: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder(it)
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                    onPositiveButton.invoke()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    // no-op
                }
                .setCancelable(true)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
