package com.animebiru.kerjaaja.presentation.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentHistoryBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProjectEntity
import com.animebiru.kerjaaja.presentation.adapter.ProjectRecommendationAdapter
import com.animebiru.kerjaaja.domain.utils.HelperDummyData
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.viewmodels.HistoryViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history),
    OnProjectCardClickListener
{

    private val binding by viewBindings(FragmentHistoryBinding::bind)
    private val historyViewModel: HistoryViewModel by viewModels()
    private val adapter by lazy { ProjectRecommendationAdapter(true, this) }
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ctlHistoryPage.setupWithNavController(binding.mtbHistoryPage, findNavController(), appBarConfiguration)
        binding.rvHistoryPage.apply {
            this.adapter = this@HistoryFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        historyViewModel.historyPager.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData.map { it.toProjectEntity() })
        }
        binding.toggleButtonStatus.check(R.id.btnFilterByOpenStatus)
        binding.toggleButtonStatus.check(R.id.btnFilterByCloseStatus)

    }

    override fun onProjectCardClickListener(project: Project) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToDetailFragment(project.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
    }

}