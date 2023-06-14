package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetExistingProjectCategoryDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class GetExistingProjectCategoryDto(
    val code: Int,
    val data: DataGetExistingProjectCategoryDto,
    val meta: MetaDto,
    val status: String,
)