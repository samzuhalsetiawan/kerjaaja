package com.animebiru.kerjaaja.presentation.view_holder

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.presentation.adapter.ProjectRecommendationAdapter
import com.animebiru.kerjaaja.presentation.listener.OnProjectViewModelDataChangeListener
import com.animebiru.kerjaaja.presentation.ui.profile.ProfileFragmentDirections
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel

class TabProjectViewHolder(private val view: View):
    RecyclerView.ViewHolder(view),
    OnProjectViewModelDataChangeListener
{

    private val rvTabJobs by lazy { view.rootView as RecyclerView }
    private val viewLifecycleOwner by lazy { view.findViewTreeLifecycleOwner() }
    private val projectRecommendationAdapter by lazy {
        ProjectRecommendationAdapter(showStatus = false) {
            val action = ProfileFragmentDirections.actionProfileFragmentToDetailFragment(it.id)
            view.findNavController().navigate(action)
        }
    }

    override fun onProjectPagerDataChange(data: PagingData<ProjectEntity>) {
        val lifecycle = viewLifecycleOwner?.lifecycle ?: return
        projectRecommendationAdapter.submitData(lifecycle, data)
    }

    fun bind() {
        rvTabJobs.apply {
            adapter = projectRecommendationAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}