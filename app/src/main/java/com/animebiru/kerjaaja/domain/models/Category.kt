package com.animebiru.kerjaaja.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes

data class Category(
    val title: String,

    @IdRes
    @DrawableRes
    val image: Int
)
