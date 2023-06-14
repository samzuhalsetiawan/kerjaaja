package com.animebiru.kerjaaja.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.animebiru.kerjaaja.R
import com.google.android.gms.maps.model.LatLng

data class Project(
    val id: String,
    val creator: String,
    val photoUrl: String,
    val shortJobDesc: String,
    val fee: String,
    val status: String,
    val createdAt: String,
    val categories: List<String>,
    val latLng: LatLng,
    @IdRes
    @DrawableRes
    val placeholder: Int = R.drawable.default_photo_profile
)