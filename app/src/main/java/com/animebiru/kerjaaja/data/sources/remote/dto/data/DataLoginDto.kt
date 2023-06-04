package com.animebiru.kerjaaja.data.sources.remote.dto.data

import com.animebiru.kerjaaja.data.sources.remote.dto.attribute.AttributeLoginDto

data class DataLoginDto(
    val type: String,
    val attributes: AttributeLoginDto
)
