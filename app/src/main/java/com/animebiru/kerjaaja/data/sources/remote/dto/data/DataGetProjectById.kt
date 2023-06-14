package com.animebiru.kerjaaja.data.sources.remote.dto.data

import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.AttributeGetExistingProjectDto
import com.animebiru.kerjaaja.data.sources.remote.dto.data.attribute.record.ProjectDto

data class DataGetProjectById(
    val type: String,
    val attributes: ProjectDto
)
