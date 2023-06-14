package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.databinding.CardCarouselCategoryBinding
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProjectCategory
import com.animebiru.kerjaaja.presentation.callback.ProjectCategoryEntityItemDiffCallback
import com.animebiru.kerjaaja.presentation.listener.OnProjectCategoryCardClickListener
import com.animebiru.kerjaaja.presentation.view_holder.CardCarouselCategoryViewHolder

class ProjectCategoryCarouselAdapter(
    private val onProjectCategoryCardClickListener: OnProjectCategoryCardClickListener? = null
): PagingDataAdapter<ProjectCategoryEntity, CardCarouselCategoryViewHolder>(ProjectCategoryEntityItemDiffCallback) {

    override fun onBindViewHolder(holder: CardCarouselCategoryViewHolder, position: Int) {
        val projectCategoryEntity = getItem(position) ?: return
        holder.bind(projectCategoryEntity.toProjectCategory())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardCarouselCategoryViewHolder {
        val binding = CardCarouselCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardCarouselCategoryViewHolder(binding, onProjectCategoryCardClickListener)
    }
}