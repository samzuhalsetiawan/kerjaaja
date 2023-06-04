package com.animebiru.kerjaaja.domain.utils

import android.content.res.Resources
import android.util.TypedValue
import com.animebiru.kerjaaja.domain.enums.Gender

object ExtensionFunctions {

    private fun Number.toPx(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)
    }

    val Number.dp: Float
        get() {
            return this.toFloat().toPx()
        }

    fun String.toGender(): Gender? {
        return Gender.values().find { it.label.lowercase() == this.lowercase() }
    }
}