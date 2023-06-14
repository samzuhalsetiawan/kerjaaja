package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class GetExistingProjectDto(
    val code: Int,
    val data: DataGetExistingProjectDto,
    val meta: MetaDto,
    val status: String
)
