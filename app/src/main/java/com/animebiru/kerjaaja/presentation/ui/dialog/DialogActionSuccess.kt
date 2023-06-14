package com.animebiru.kerjaaja.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.presentation.listener.OnDialogPositiveButtonClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogActionSuccess: DialogFragment() {

    var title = "Success"
    var message = "Message Not Provided"
    var positiveButtonLabel = "Ok"
    var positiveButtonListener: OnDialogPositiveButtonClickListener? = null

    private val materialAlertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonLabel){ di, _ ->
                positiveButtonListener?.onDialogPositiveButtonClickListener(di)
                dismiss()
            }
            .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return materialAlertDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        positiveButtonListener = null
    }

}