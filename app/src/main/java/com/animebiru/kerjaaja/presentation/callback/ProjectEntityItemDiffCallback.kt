package com.animebiru.kerjaaja.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity

object ProjectEntityItemDiffCallback: DiffUtil.ItemCallback<ProjectEntity>() {
    override fun areItemsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
        return oldItem == newItem
    }
}