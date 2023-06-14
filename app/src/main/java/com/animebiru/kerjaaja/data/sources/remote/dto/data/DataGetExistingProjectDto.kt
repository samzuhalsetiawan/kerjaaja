package com.animebiru.kerjaaja.data.sources.remote.dto.data

import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.AttributeGetExistingProjectDto

data class DataGetExistingProjectDto(
    val type: String,
    val attributes: AttributeGetExistingProjectDto
)
