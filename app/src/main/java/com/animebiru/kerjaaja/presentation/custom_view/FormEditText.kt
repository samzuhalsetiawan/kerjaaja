package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

abstract class FormEditText : TextInputLayout, TextWatcher {

    private var prevErrorState: Boolean = true
    private var onErrorStateChange: OnErrorStateChange? = null

    abstract var errorText: String?

    /**
     * This method called when editTextEvent [onTextChanged] triggered and
     * you must determine error should be displayed or not based on return value
     * @param text input text from editText
     * @return true if valid, otherwise false
     */
    abstract fun isInputValid(text: String): Boolean

    private fun init() { editText?.addTextChangedListener(this) }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s ?: return
        val isValid = isInputValid(s.toString())
        if (isValid) closeError() else showError()
        if (prevErrorState != isErrorEnabled) {
            prevErrorState = isErrorEnabled
            onErrorStateChange?.onErrorStateChange(isErrorEnabled)
        }
    }

    private fun closeError() {
        isErrorEnabled = false
        error = null
    }

    private fun showError() {
        error = errorText
        isErrorEnabled = true
    }

    fun addOnErrorStateChange(listener: OnErrorStateChange) {
        onErrorStateChange = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onErrorStateChange = null
        editText?.removeTextChangedListener(this)
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    fun interface OnErrorStateChange {
        fun onErrorStateChange(isErrorEnabled: Boolean)
    }
}