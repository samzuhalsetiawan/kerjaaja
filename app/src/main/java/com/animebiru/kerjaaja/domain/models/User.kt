package com.animebiru.kerjaaja.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    val createdAt: String,
    val fullName: String,
    val gender: String,
    val photoUrl: String,
    val updatedAt: String,
    val username: String
)