package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.databinding.ListItemSearchQueryBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.presentation.callback.ProjectItemDiffCallback
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.view_holder.SearchProjectViewHolder

class SearchProjectAdapter(private val listener: OnProjectCardClickListener): ListAdapter<Project, SearchProjectViewHolder>(ProjectItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProjectViewHolder {
        return SearchProjectViewHolder(
            ListItemSearchQueryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: SearchProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}