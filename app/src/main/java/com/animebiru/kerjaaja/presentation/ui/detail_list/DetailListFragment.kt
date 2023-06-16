package com.animebiru.kerjaaja.presentation.ui.detail_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentDetailListBinding
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.adapter.CategoryDetailListAdapter
import com.animebiru.kerjaaja.presentation.listener.OnProjectCardClickListener
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailListFragment : Fragment(R.layout.fragment_detail_list), OnProjectCardClickListener {
    private val binding by viewBindings(FragmentDetailListBinding::bind)
    private val args: DetailListFragmentArgs by navArgs()
    private val categoryName by lazy { args.categoryName }
    private val listAdapter by lazy { CategoryDetailListAdapter(this) }
    private val projectViewModel: ProjectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectViewModel.getProjectsByCategories(listOf(categoryName))

        binding.mtbDetailCategoryList.setupWithNavController(findNavController())
        binding.mtbDetailCategoryList.title = categoryName

        setupProjectRecyclerView()

        collectLatestFlowWhenStarted(projectViewModel.projectByCategories) {
            listAdapter.submitList(it)
        }
    }

    private fun setupProjectRecyclerView() {
        binding.rvDetailCategoryList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onProjectCardClickListener(project: Project) {
        val action = DetailListFragmentDirections.actionDetailListFragmentToDetailFragment(project.id)
        findNavController().navigate(action)
    }
}