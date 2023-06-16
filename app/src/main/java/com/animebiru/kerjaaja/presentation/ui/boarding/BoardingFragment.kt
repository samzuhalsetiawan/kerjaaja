package com.animebiru.kerjaaja.presentation.ui.boarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentBoardingBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.adapter.OnBoardingAdapter

class BoardingFragment : Fragment(R.layout.fragment_boarding) {
    val binding by viewBindings(FragmentBoardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpOnBoarding.adapter = OnBoardingAdapter(this)
        binding.vpOnBoarding.isUserInputEnabled = false

    }

}