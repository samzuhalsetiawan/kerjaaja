package com.animebiru.kerjaaja.data.sources.remote.dto.data

import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.AttributeGetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.AttributeGetUserByUsernameDto

data class DataGetUserByUsernameDto(
    val type: String,
    val attributes: AttributeGetUserByUsernameDto
)
