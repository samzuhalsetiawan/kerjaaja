package com.animebiru.kerjaaja.domain.utils

import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.models.Chat
import com.animebiru.kerjaaja.domain.models.Project

object HelperDummyData {
    private const val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vulputate sem dui, vitae hendrerit lacus pretium quis. Aenean maximus vehicula sem eu tempor."

    val dummyChat = listOf(
        Chat("1", "Hello", false),
        Chat("1", "Saya ingin bertanya", false),
        Chat("1", "Silahkan", true)
    )
}