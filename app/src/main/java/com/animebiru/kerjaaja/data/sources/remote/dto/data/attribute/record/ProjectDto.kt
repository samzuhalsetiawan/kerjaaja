package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record


import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category.CategoryDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.owner.OwnerDto
import com.google.gson.annotations.SerializedName

data class ProjectDto(
    @SerializedName("categories")
    val categories: List<CategoryDto>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("fee")
    val fee: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("owner")
    val owner: OwnerDto,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)