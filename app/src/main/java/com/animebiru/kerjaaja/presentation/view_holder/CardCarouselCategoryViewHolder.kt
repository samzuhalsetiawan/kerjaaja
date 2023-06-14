package com.animebiru.kerjaaja.presentation.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.databinding.CardCarouselCategoryBinding
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.presentation.listener.OnProjectCategoryCardClickListener
import com.bumptech.glide.Glide

class CardCarouselCategoryViewHolder(
    private val binding: CardCarouselCategoryBinding,
    private val onProjectCategoryCardClickListener: OnProjectCategoryCardClickListener? = null
): RecyclerView.ViewHolder(binding.root) {

    private val ivCarouselItemCategory = binding.ivCarouselItemCategory
    private val tvLabelItemCategory = binding.tvLabelItemCategory
    private val card = binding.mflCarouselCategoryContainer

    fun bind(projectCategory: ProjectCategory) {
        Glide.with(binding.root.context)
            .load(projectCategory.photoUrl)
            .placeholder(projectCategory.placeholder)
            .into(ivCarouselItemCategory)
        tvLabelItemCategory.text = projectCategory.title
        card.setOnClickListener { onProjectCategoryCardClickListener?.onProjectCategoryCardClickListener(projectCategory) }
    }
}