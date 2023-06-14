package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataGetUserByUsernameDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class GetUserByUsernameDto(
    val code: Int,
    val data: DataGetUserByUsernameDto,
    val meta: MetaDto,
    val status: String
)