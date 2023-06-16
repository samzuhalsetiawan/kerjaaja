package com.animebiru.kerjaaja.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentHomeBinding
import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.presentation.adapter.ProjectCategoryCarouselAdapter
import com.animebiru.kerjaaja.presentation.adapter.ProjectRecommendationAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.adapter.SearchProjectAdapter
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.listener.OnProjectCategoryCardClickListener
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.search.SearchView
import com.google.android.material.search.SearchView.TransitionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    TransitionListener,
    OnProjectCardClickListener,
    OnProjectCategoryCardClickListener {

    private val binding by viewBindings(FragmentHomeBinding::bind)
    private val projectViewModel: ProjectViewModel by viewModels()
    private lateinit var projectCategoryCarouselAdapter: ProjectCategoryCarouselAdapter
    private lateinit var projectRecommendationAdapter: ProjectRecommendationAdapter
    private lateinit var projectSearchAdapter: SearchProjectAdapter
    private lateinit var projectRecommendationLayoutManager: LinearLayoutManager
    private var originalMode : Int? = null

    override fun onStart() {
        super.onStart()
        originalMode = activity?.window?.attributes?.softInputMode
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svMainSearchView.addTransitionListener(this)
        setupCategoryCarousel()
        setupJobRecommendationList()
        setupProjectSearchList()
        projectViewModel.projectCategoryPager.observe(viewLifecycleOwner) { projectCategoryCarouselAdapter.submitData(lifecycle, it) }
        projectViewModel.projectPager.observe(viewLifecycleOwner) { projectRecommendationAdapter.submitData(lifecycle, it) }
        
        collectLatestFlowWhenStarted(projectRecommendationAdapter.loadStateFlow) { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    val smoothScroller = object: LinearSmoothScroller(requireContext()) {
                        override fun getVerticalSnapPreference(): Int {
                            return SNAP_TO_START
                        }
                    }.apply { targetPosition = 0 }
                    projectRecommendationLayoutManager.startSmoothScroll(smoothScroller)
                    binding.srlRefreshLayout.isRefreshing = false
                }
                is LoadState.Loading -> binding.srlRefreshLayout.isRefreshing = true
                is LoadState.Error -> Unit
            }
        }

        collectLatestFlowWhenStarted(projectViewModel.searchProject) {
            projectSearchAdapter.submitList(it)
        }

        binding.srlRefreshLayout.setOnRefreshListener {
            projectRecommendationAdapter.refresh()
        }
        binding.fabCreateJob.setOnClickListener { navigateToCreateProject() }

        binding.svMainSearchView.editText.setOnEditorActionListener { v, actionId, event ->
            val query = binding.svMainSearchView.text.toString()
            projectViewModel.searchProject(query)
            false
        }
    }

    private fun navigateToCreateProject() {
        val action = HomeFragmentDirections.actionHomeFragmentToCreateProjectFragment()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
    }


    override fun onProjectCardClickListener(project: Project) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(project.id)
        findNavController().navigate(action)
    }

    override fun onProjectCategoryCardClickListener(projectCategory: ProjectCategory) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailListFragment(projectCategory.title)
        findNavController().navigate(action)
    }

    override fun onStateChanged(
        searchView: SearchView,
        previousState: SearchView.TransitionState,
        newState: SearchView.TransitionState
    ) {
        when (newState) {
            SearchView.TransitionState.SHOWING -> binding.fabCreateJob.hide()
            SearchView.TransitionState.HIDDEN -> binding.fabCreateJob.show()
            else -> Unit
        }
    }

    private fun setupJobRecommendationList() {
        projectRecommendationAdapter = ProjectRecommendationAdapter(false, this)
        projectRecommendationLayoutManager = LinearLayoutManager(requireContext())
        binding.rvRecommendation.apply {
            adapter = projectRecommendationAdapter
            layoutManager = projectRecommendationLayoutManager
        }
    }

    private fun setupProjectSearchList() {
        projectSearchAdapter = SearchProjectAdapter(this)
        binding.rvSearchResult.apply {
            adapter = projectSearchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupCategoryCarousel() {
        projectCategoryCarouselAdapter = ProjectCategoryCarouselAdapter(this)
        binding.rvCarouselCategory.apply {
            adapter = projectCategoryCarouselAdapter
            layoutManager = CarouselLayoutManager()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }
}