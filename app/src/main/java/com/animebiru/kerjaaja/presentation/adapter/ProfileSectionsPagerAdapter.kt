package com.animebiru.kerjaaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.presentation.listener.OnProjectViewModelDataChangeListener
import com.animebiru.kerjaaja.presentation.view_holder.TabAppearanceViewHolder
import com.animebiru.kerjaaja.presentation.view_holder.TabProjectViewHolder
import com.animebiru.kerjaaja.presentation.view_holder.TabSecurityViewHolder

class ProfileSectionsPagerAdapter:
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    OnProjectViewModelDataChangeListener
{
    private var tabProjectViewHolder: TabProjectViewHolder? = null

    override fun onProjectPagerDataChange(data: PagingData<ProjectEntity>) {
        tabProjectViewHolder?.onProjectPagerDataChange(data)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TAB_APPEARANCE
            1 -> TAB_JOBS
            2 -> TAB_SECURITY
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TAB_APPEARANCE -> TabAppearanceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_appearance, parent, false))
            TAB_JOBS -> TabProjectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_jobs, parent, false)).also { tabProjectViewHolder = it }
            TAB_SECURITY -> TabSecurityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_tab_security, parent, false))
            else -> throw Exception("Tabs not implemented, viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TabAppearanceViewHolder -> Unit
            is TabProjectViewHolder -> holder.bind()
            is TabSecurityViewHolder -> Unit
        }
    }

    override fun getItemCount(): Int = 3

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        tabProjectViewHolder = null
    }

    companion object {
        private const val TAB_APPEARANCE = 0
        private const val TAB_JOBS = 1
        private const val TAB_SECURITY = 2
    }
}