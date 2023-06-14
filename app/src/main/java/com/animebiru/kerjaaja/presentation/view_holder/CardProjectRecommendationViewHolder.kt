package com.animebiru.kerjaaja.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.databinding.CardProjectRecommendationBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.bumptech.glide.Glide

class CardProjectRecommendationViewHolder(
    private val binding: CardProjectRecommendationBinding,
    private val showStatus: Boolean = false,
    private val onProjectCardClickListener: OnProjectCardClickListener? = null
): RecyclerView.ViewHolder(binding.root) {
    private val ivProfilePicture = binding.ivProfilePicture
    private val tvUsername = binding.tvUsername
    private val chipFee = binding.chipFee
    private val chipStatus = binding.chipStatus
    private val tvDescription = binding.tvDescription
    private val btnDetail = binding.btnDetail

    fun bind(project: Project) {
        Glide.with(binding.root.context)
            .load(project.photoUrl)
            .placeholder(project.placeholder)
            .circleCrop()
            .into(ivProfilePicture)

        tvUsername.text = project.creator
        if (showStatus) {
            chipFee.visibility = View.GONE
            chipStatus.visibility = View.VISIBLE
            chipStatus.text = project.status
        } else {
            chipFee.visibility = View.VISIBLE
            chipStatus.visibility = View.GONE
            chipFee.text = project.fee
        }
        tvDescription.text = project.shortJobDesc
        btnDetail.setOnClickListener { onProjectCardClickListener?.onProjectCardClickListener(project) }
    }
}