package com.animebiru.kerjaaja.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.animebiru.kerjaaja.R

data class ProjectCategory(
    val title: String,
    val photoUrl: String,
    val createdAt: Long,
    @IdRes
    @DrawableRes
    val placeholder: Int = R.drawable.ic_broken_image
)
