package com.animebiru.kerjaaja.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes

data class Job(
    val id: String,

    val creator: String,

    @IdRes
    @DrawableRes
    val profilePicture: Int,

    val fee: Int,

    val shortJobDesc: String
)