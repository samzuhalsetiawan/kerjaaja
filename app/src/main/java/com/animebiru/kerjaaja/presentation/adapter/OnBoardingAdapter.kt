package com.animebiru.kerjaaja.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.animebiru.kerjaaja.presentation.ui.boarding.questions.TrueFalseBoardingFragment

class OnBoardingAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return TrueFalseBoardingFragment()
    }
}