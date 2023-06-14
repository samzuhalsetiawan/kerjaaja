package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute

import com.google.gson.annotations.SerializedName

data class AttributeLoginDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_role")
    val userRole: String
)