package com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute

import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.ProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.UserDto
import com.google.gson.annotations.SerializedName

data class AttributeGetUserByUsernameDto(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data_count_on_current_page")
    val dataCountOnCurrentPage: Int,
    @SerializedName("records")
    val records: List<UserDto>,
    @SerializedName("total_data_count")
    val totalDataCount: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)