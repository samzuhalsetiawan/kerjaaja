package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataLoginDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class LoginDto(
    val code: Int,
    val data: DataLoginDto,
    val meta: MetaDto,
    val status: String,
)