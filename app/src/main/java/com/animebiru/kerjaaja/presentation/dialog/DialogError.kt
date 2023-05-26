package com.animebiru.kerjaaja.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.animebiru.kerjaaja.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogError : DialogFragment() {

    private var _defaultTitle = "Error"
    private var _defaultMessage = "Error Message Not Provided"
    private var _listener: DialogErrorListener? = null

    private val materialAlertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(_defaultTitle)
            .setMessage(_defaultMessage)
            .setPositiveButton(R.string.label_dialog_positive_button){ _,_ ->
                _listener?.onPositiveButtonClicked()
                dismiss()
            }
            .create()
    }

    fun showDialog(fragmentManager: FragmentManager) {
        show(fragmentManager, TAG)
    }

    fun setTitle(title: String) {
        _defaultTitle = title
    }

    fun setMessage(message: String) {
        _defaultMessage = message
    }

    fun setListener(listener: DialogErrorListener) {
        _listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return materialAlertDialog
    }

    fun interface DialogErrorListener {
        fun onPositiveButtonClicked()
    }

    companion object {
        private const val TAG = "com.animebiru.kerjaaja.DIALOG_ERROR"
    }

}