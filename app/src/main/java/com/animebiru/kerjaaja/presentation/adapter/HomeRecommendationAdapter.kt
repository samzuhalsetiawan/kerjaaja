package com.animebiru.kerjaaja.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.domain.models.Job
import com.animebiru.kerjaaja.databinding.CardRecommendationJobBinding
import com.bumptech.glide.Glide

class HomeRecommendationAdapter : RecyclerView.Adapter<HomeRecommendationAdapter.ViewHolder>() {

    var listener: HomeRecommendationAdapterListener? = null

    inner class ViewHolder(private val binding: CardRecommendationJobBinding): RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
        val ivProfilePicture = binding.ivProfilePicture
        val tvUsername = binding.tvUsername
        val chipFee = binding.chipFee
        val tvDescription = binding.tvDescription
        val btnDetail = binding.btnDetail
    }

    private val differCallback = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var listOfJob: List<Job>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardRecommendationJobBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = listOfJob[position]

        holder.apply {
            Glide.with(context)
                .load(job.profilePicture)
                .circleCrop()
                .into(ivProfilePicture)

            tvUsername.text = job.creator
            chipFee.text = job.fee.toString()
            tvDescription.text = job.shortJobDesc
            btnDetail.setOnClickListener { listener?.onDetailJobCardClicked() }
        }

    }

    override fun getItemCount(): Int = listOfJob.size

    fun interface HomeRecommendationAdapterListener {
        fun onDetailJobCardClicked()
    }
}