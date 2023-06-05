package com.animebiru.kerjaaja.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentHomeBinding
import com.animebiru.kerjaaja.presentation.adapter.HomeCarouselAdapter
import com.animebiru.kerjaaja.presentation.adapter.HomeRecommendationAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.search.SearchView
import com.google.android.material.search.SearchView.TransitionListener

class HomeFragment : Fragment(R.layout.fragment_home),
    TransitionListener,
    HomeRecommendationAdapter.HomeRecommendationAdapterListener {

    private val binding by viewBindings(FragmentHomeBinding::bind)
    private lateinit var homeCarouselAdapter: HomeCarouselAdapter
    private lateinit var homeRecommendationAdapter: HomeRecommendationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svMainSearchView.addTransitionListener(this)
        setupCategoryCarousel()
        setupJobRecommendationList()
        binding.fabCreateJob.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateProjectFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
    }

    override fun onDetailJobCardClicked() {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
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
        homeRecommendationAdapter = HomeRecommendationAdapter().apply { listener = this@HomeFragment }
        binding.rvRecommendation.apply {
            adapter = homeRecommendationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        homeRecommendationAdapter.listOfJob = HelperDummyData.dummyJob
    }

    private fun setupCategoryCarousel() {
        homeCarouselAdapter = HomeCarouselAdapter(HelperDummyData.dummyCategories)
        binding.rvCarouselCategory.apply {
            adapter = homeCarouselAdapter
            layoutManager = CarouselLayoutManager()
            setHasFixedSize(true)
        }
    }
}