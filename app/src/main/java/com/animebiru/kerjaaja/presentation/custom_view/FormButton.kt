package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.button.MaterialButton

class FormButton : MaterialButton {

    private val textFieldErrorStates = mutableMapOf<Int, Boolean>()

    fun setupWithEditText(vararg textFields: FormEditText) {
        for (textField in textFields) {
            textField.addOnErrorStateChange { isError ->
                textFieldErrorStates[textField.id] = isError
                val isAllFieldFilled = textFields.none { it.editText?.text.toString().isBlank() }
                val isAllFieldValid = textFieldErrorStates.none { it.value }
                if (isAllFieldValid && isAllFieldFilled) enabledButton() else disabledButton()
            }
        }
    }

    private fun enabledButton() {
        isEnabled = true
    }

    private fun disabledButton() {
        isEnabled = false
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

}