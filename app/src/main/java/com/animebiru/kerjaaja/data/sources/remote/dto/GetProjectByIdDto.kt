package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetProjectById
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class GetProjectByIdDto(
    val code: Int,
    val data: DataGetProjectById,
    val meta: MetaDto,
    val status: String,
)
