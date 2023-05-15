package com.animebiru.kerjaaja.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.models.Category
import com.animebiru.kerjaaja.data.models.Job
import com.animebiru.kerjaaja.databinding.FragmentHomeBinding
import com.animebiru.kerjaaja.domain.adapter.HomeCarouselAdapter
import com.animebiru.kerjaaja.domain.adapter.HomeRecommendationAdapter
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeCarouselAdapter: HomeCarouselAdapter
    private lateinit var homeRecommendationAdapter: HomeRecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vulputate sem dui, vitae hendrerit lacus pretium quis. Aenean maximus vehicula sem eu tempor."

        val dummyCategories = listOf(
            Category("Technology", R.drawable.dummy1),
            Category("Creative", R.drawable.dummy2),
            Category("Service", R.drawable.dummy3),
            Category("Dummy4", R.drawable.dummy4),
            Category("Dummy5", R.drawable.dummy5),
            Category("Dummy6", R.drawable.dummy6)
        )

        val dummyJob = listOf(
            Job("1", "Anonymous", R.drawable.pp_small, 10000, lorem),
            Job("2", "Anonymous", R.drawable.pp_small, 10000, lorem),
            Job("3", "Anonymous", R.drawable.pp_small, 10000, lorem),
            Job("4", "Anonymous", R.drawable.pp_small, 10000, lorem),
            Job("5", "Anonymous", R.drawable.pp_small, 10000, lorem),
        )

        homeCarouselAdapter = HomeCarouselAdapter(dummyCategories)
        homeRecommendationAdapter = HomeRecommendationAdapter()

        binding.rvCarouselCategory.apply {
            adapter = homeCarouselAdapter
            layoutManager = CarouselLayoutManager()
            setHasFixedSize(true)
        }

        binding.rvRecommendation.apply {
            adapter = homeRecommendationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        homeRecommendationAdapter.listOfJob = dummyJob

    }

}