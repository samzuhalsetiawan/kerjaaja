package com.animebiru.kerjaaja.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import com.animebiru.kerjaaja.domain.models.Project

object ProjectItemDiffCallback: DiffUtil.ItemCallback<Project>() {

    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}