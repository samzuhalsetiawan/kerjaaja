package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)