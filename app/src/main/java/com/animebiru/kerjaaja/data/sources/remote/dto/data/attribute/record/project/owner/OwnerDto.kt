package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.owner


import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("photo_url")
    val photoUrl: String
)