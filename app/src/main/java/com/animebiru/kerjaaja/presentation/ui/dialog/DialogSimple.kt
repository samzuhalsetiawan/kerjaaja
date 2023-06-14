package com.animebiru.kerjaaja.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.animebiru.kerjaaja.presentation.listener.OnDialogPositiveButtonClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogSimple: DialogFragment() {

    var title = "Title"
    var message = "Message"
    var positiveButtonLabel = "Ok"
    var onPositiveButtonClickListener: OnDialogPositiveButtonClickListener? = null

    private val materialAlertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonLabel) { di, _ ->
                onPositiveButtonClickListener?.onDialogPositiveButtonClickListener(di)
                dismiss()
            }.create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return materialAlertDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onPositiveButtonClickListener = null
    }
}