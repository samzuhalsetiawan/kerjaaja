package com.animebiru.kerjaaja.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentProfileBinding
import com.animebiru.kerjaaja.domain.adapter.ProfileSectionsPagerAdapter
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBindings(FragmentProfileBinding::bind)
    private val profileSectionsPagerAdapter by lazy { ProfileSectionsPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mtbProfileFragment.setupWithNavController(findNavController())
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())

        val viewPager: ViewPager2 = binding.vp2ProfilePage
        val tabs: TabLayout = binding.tlProfilePage
        viewPager.adapter = profileSectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
    }
}