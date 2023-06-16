package com.animebiru.kerjaaja.presentation.ui.boarding.questions

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentTrueFalseBoardingBinding
import com.animebiru.kerjaaja.domain.utils.MLHelper
import com.animebiru.kerjaaja.domain.utils.viewBindings

class TrueFalseBoardingFragment : Fragment(R.layout.fragment_true_false_boarding) {
    private val binding by viewBindings(FragmentTrueFalseBoardingBinding::bind)
    private val btnTrueSizeAnimator by lazy { ValueAnimator.ofFloat(1f, 1.3f).also { it.duration = 1000 } }
    private val btnFalseSizeAnimator by lazy { ValueAnimator.ofFloat(1f, 1.3f).also { it.duration = 1000 } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvStatement.text = MLHelper.statements.random()

        binding.lavTrue.frame = 129
        binding.lavFalse.frame = 129

        btnTrueSizeAnimator.addUpdateListener {
            binding.lavTrue.scaleX = (it.animatedValue as Float)
            binding.lavTrue.scaleY = (it.animatedValue as Float)
        }

        btnFalseSizeAnimator.addUpdateListener {
            binding.lavFalse.scaleX = (it.animatedValue as Float)
            binding.lavFalse.scaleY = (it.animatedValue as Float)
        }

        binding.lavTrue.setOnClickListener {
            binding.lavTrue.playAnimation()
            btnTrueSizeAnimator.start()
            if (binding.lavFalse.scaleX == 1.3f) {
                btnFalseSizeAnimator.reverse()
            }
        }
        binding.lavFalse.setOnClickListener {
            binding.lavFalse.playAnimation()
            btnFalseSizeAnimator.start()
            if (binding.lavTrue.scaleX == 1.3f) {
                btnTrueSizeAnimator.reverse()
            }
        }

        binding.btnNext.setOnClickListener {
            val viewPager = binding.root.rootView.findViewById<ViewPager2>(R.id.vpOnBoarding)
            val maxPage = viewPager.adapter?.itemCount ?: 0
            if (viewPager.currentItem < maxPage - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            } else {
                findNavController().navigate(R.id.action_boardingFragment_to_homeFragment)
            }
        }

    }

}