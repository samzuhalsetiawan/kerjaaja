package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Button
import com.animebiru.kerjaaja.R
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText: FormEditText {

    override var errorText: String? = resources.getString(R.string.error_text_password_etl)
    private var passwordMatcherEditText: PasswordEditText? = null

    override fun isInputValid(text: String): Boolean {
        return if (passwordMatcherEditText == null) {
            text.length >= 8
        } else {
            val password = passwordMatcherEditText?.editText?.text ?: return false
            password.toString() == text
        }
    }

    fun setupMatchingWithEditText(passwordEditText: PasswordEditText) {
        errorText = resources.getString(R.string.error_text_confirm_password_etl)
        passwordMatcherEditText = passwordEditText
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
}