package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category.project_category


import com.google.gson.annotations.SerializedName

data class ProjectCategoryDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String
)