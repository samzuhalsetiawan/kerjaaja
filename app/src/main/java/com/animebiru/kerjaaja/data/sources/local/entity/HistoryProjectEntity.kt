package com.animebiru.kerjaaja.data.sources.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("projects_history")
data class HistoryProjectEntity(
    @PrimaryKey
    @ColumnInfo("project_id")
    val id: String,
    val creator: String,
    @ColumnInfo("photo_url")
    val photoUrl: String,
    @ColumnInfo("short_job_desc")
    val shortJobDesc: String,
    val status: String,
    val fee: String,
    @ColumnInfo("created_at")
    val createdAt: String,
    val latitude: String,
    val longitude: String,
    val page: Int
)
