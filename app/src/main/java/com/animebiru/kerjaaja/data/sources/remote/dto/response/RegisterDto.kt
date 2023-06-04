package com.animebiru.kerjaaja.data.sources.remote.dto.response

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataRegisterDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class RegisterDto(
    val code: Int,
    val data: DataRegisterDto,
    val meta: MetaDto,
    val status: String,
)