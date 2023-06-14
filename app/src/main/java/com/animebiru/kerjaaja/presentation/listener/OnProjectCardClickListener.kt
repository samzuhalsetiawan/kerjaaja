package com.animebiru.kerjaaja.presentation.listener

import com.animebiru.kerjaaja.domain.models.Project

fun interface OnProjectCardClickListener {
    fun onProjectCardClickListener(project: Project)
}