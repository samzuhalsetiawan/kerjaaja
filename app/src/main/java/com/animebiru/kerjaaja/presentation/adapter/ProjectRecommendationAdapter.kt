package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.databinding.CardProjectRecommendationBinding
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProject
import com.animebiru.kerjaaja.presentation.callback.ProjectEntityItemDiffCallback
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.view_holder.CardProjectRecommendationViewHolder

class ProjectRecommendationAdapter(
    private val showStatus: Boolean = false,
    private val onProjectCardClickListener: OnProjectCardClickListener
) : PagingDataAdapter<ProjectEntity, CardProjectRecommendationViewHolder>(ProjectEntityItemDiffCallback) {

    override fun onBindViewHolder(holder: CardProjectRecommendationViewHolder, position: Int) {
        val projectEntity = getItem(position) ?: return
        holder.bind(projectEntity.toProject())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardProjectRecommendationViewHolder {
        val binding = CardProjectRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardProjectRecommendationViewHolder(binding, showStatus, onProjectCardClickListener)
    }

}