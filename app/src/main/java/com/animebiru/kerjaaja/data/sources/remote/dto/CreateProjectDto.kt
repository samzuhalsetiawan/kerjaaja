package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataCreateProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetExistingProjectCategoryDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class CreateProjectDto(
    val code: Int,
    val data: DataCreateProjectDto,
    val meta: MetaDto,
    val status: String,
)