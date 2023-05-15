package com.animebiru.kerjaaja.domain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.data.models.Category
import com.animebiru.kerjaaja.databinding.CardCarouselCategoryBinding
import com.bumptech.glide.Glide

class HomeCarouselAdapter(private val listOfCategory: List<Category>) : RecyclerView.Adapter<HomeCarouselAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardCarouselCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
        val ivCarouselItemCategory = binding.ivCarouselItemCategory
        val tvLabelItemCategory = binding.tvLabelItemCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardCarouselCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listOfCategory[position]

        Glide.with(holder.context)
            .load(category.image)
            .into(holder.ivCarouselItemCategory)

        holder.tvLabelItemCategory.text = category.title
    }

    override fun getItemCount(): Int = listOfCategory.size
}