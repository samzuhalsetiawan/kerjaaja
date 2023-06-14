package com.animebiru.kerjaaja.presentation.listener

import androidx.paging.PagingData
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity

interface OnProjectViewModelDataChangeListener {
    fun onProjectPagerDataChange(data: PagingData<ProjectEntity>)
}