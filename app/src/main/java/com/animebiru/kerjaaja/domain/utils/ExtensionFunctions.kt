package com.animebiru.kerjaaja.domain.utils

import android.content.res.Resources
import android.util.TypedValue

object ExtensionFunctions {

    private fun Number.toPx(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)
    }

    val Number.dp: Float
        get() {
            return this.toFloat().toPx()
        }
}