package com.animebiru.kerjaaja.presentation.listener

import com.animebiru.kerjaaja.domain.models.ProjectCategory

fun interface OnProjectCategoryCardClickListener {
    fun onProjectCategoryCardClickListener(projectCategory: ProjectCategory)
}