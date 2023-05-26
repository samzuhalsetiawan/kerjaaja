package com.animebiru.kerjaaja.presentation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentHistoryBinding
import com.animebiru.kerjaaja.domain.adapter.HomeRecommendationAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBindings(FragmentHistoryBinding::bind)
    private val adapter by lazy { HomeRecommendationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbHistoryPage.setupWithNavController(findNavController())
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
        binding.rvHistoryPage.apply {
            this.adapter = this@HistoryFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        adapter.listOfJob = HelperDummyData.dummyJob
    }

}