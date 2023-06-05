package com.animebiru.kerjaaja.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import com.animebiru.kerjaaja.R

class TextEditText : FormEditText {

    override var errorText: String? = resources.getString(R.string.error_text_empty_text_field)

    override fun isInputValid(text: String): Boolean {
        return text.isNotBlank()
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

}