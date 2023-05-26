package com.animebiru.kerjaaja.presentation.create_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentCreateProjectBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings

class CreateProjectFragment : Fragment(R.layout.fragment_create_project) {

    private val binding by viewBindings(FragmentCreateProjectBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbCreateJobPage.setupWithNavController(findNavController())
    }

}