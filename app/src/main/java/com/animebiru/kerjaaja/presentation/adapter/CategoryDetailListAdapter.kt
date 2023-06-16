package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.animebiru.kerjaaja.databinding.CardProjectRecommendationBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.presentation.callback.ProjectItemDiffCallback
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.view_holder.CardProjectRecommendationViewHolder

class CategoryDetailListAdapter(
    private val listener: OnProjectCardClickListener
): ListAdapter<Project, CardProjectRecommendationViewHolder>(ProjectItemDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardProjectRecommendationViewHolder {
        return CardProjectRecommendationViewHolder(
            CardProjectRecommendationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            false,
            listener
        )
    }

    override fun onBindViewHolder(holder: CardProjectRecommendationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}