package com.animebiru.kerjaaja.domain.utils

import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.models.Category
import com.animebiru.kerjaaja.data.models.Chat
import com.animebiru.kerjaaja.data.models.Job

object HelperDummyData {
    private const val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vulputate sem dui, vitae hendrerit lacus pretium quis. Aenean maximus vehicula sem eu tempor."

    val dummyCategories = listOf(
        Category("Technology", R.drawable.dummy1),
        Category("Creative", R.drawable.dummy2),
        Category("Service", R.drawable.dummy3),
        Category("Dummy4", R.drawable.dummy4),
        Category("Dummy5", R.drawable.dummy5),
        Category("Dummy6", R.drawable.dummy6)
    )

    val dummyJob = listOf(
        Job("1", "Anonymous", R.drawable.pp_small, 10000, lorem),
        Job("2", "Anonymous", R.drawable.pp_small, 10000, lorem),
        Job("3", "Anonymous", R.drawable.pp_small, 10000, lorem),
        Job("4", "Anonymous", R.drawable.pp_small, 10000, lorem),
        Job("5", "Anonymous", R.drawable.pp_small, 10000, lorem),
    )

    val dummyChat = listOf(
        Chat("1", "Hello", false),
        Chat("1", "Saya ingin bertanya", false),
        Chat("1", "Silahkan", true)
    )
}