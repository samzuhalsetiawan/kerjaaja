package com.animebiru.kerjaaja.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity

object ProjectCategoryEntityItemDiffCallback: DiffUtil.ItemCallback<ProjectCategoryEntity>() {
    override fun areItemsTheSame(
        oldItem: ProjectCategoryEntity,
        newItem: ProjectCategoryEntity
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: ProjectCategoryEntity,
        newItem: ProjectCategoryEntity
    ): Boolean {
        return oldItem == newItem
    }
}