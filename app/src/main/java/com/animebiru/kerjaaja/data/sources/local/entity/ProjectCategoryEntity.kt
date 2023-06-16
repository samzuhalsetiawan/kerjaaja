package com.animebiru.kerjaaja.data.sources.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("project_categories")
data class ProjectCategoryEntity(
    @PrimaryKey
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("photo_url")
    val photoUrl: String,
    @ColumnInfo("created_at")
    val createdAt: Long,
    val page: Int
)