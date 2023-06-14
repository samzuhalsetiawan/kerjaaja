package com.animebiru.kerjaaja.data.sources.remote.dto

import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataRegisterDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.DataUpdateUserProfilePictureDto
import com.animebiru.kerjaaja.data.sources.remote.dto.meta.MetaDto

data class UpdateUserProfilePictureDto(
    val code: Int,
    val data: DataUpdateUserProfilePictureDto,
    val meta: MetaDto,
    val status: String,
)