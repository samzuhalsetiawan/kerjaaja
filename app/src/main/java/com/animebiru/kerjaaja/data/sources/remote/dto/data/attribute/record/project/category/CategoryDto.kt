package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category


import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.project.category.project_category.ProjectCategoryDto
import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("project_category")
    val projectCategory: ProjectCategoryDto,
    @SerializedName("updated_at")
    val updatedAt: String
)