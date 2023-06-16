package com.animebiru.kerjaaja.presentation.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.databinding.ListItemSearchQueryBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener

class SearchProjectViewHolder(
    private val binding: ListItemSearchQueryBinding,
    private val listener: OnProjectCardClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(project: Project) {
        binding.livSearchItem.labelText = project.shortJobDesc
        binding.livSearchItem.setOnClickListener { listener.onProjectCardClickListener(project) }
    }

}