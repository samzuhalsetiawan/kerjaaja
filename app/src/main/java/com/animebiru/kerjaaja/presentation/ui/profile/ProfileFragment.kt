package com.animebiru.kerjaaja.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentProfileBinding
import com.animebiru.kerjaaja.presentation.adapter.ProfileSectionsPagerAdapter
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.custom_view.ListItemView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBindings(FragmentProfileBinding::bind)
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private val profileSectionsPagerAdapter by lazy { ProfileSectionsPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ctlProfileFragment.setupWithNavController(binding.mtbProfileFragment, findNavController(), appBarConfiguration)
        val viewPager: ViewPager2 = binding.vp2ProfilePage
        val tabs: TabLayout = binding.tlProfilePage
        viewPager.adapter = profileSectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

//        val tabAppearanceFragment = profileSectionsPagerAdapter.onCreateViewHolder(TAB_TITLES,2)
//        if (tabAppearanceFragment is FragmentAppearance) {
//            val cvChangePhotoProfile: ListItemView = tabAppearanceFragment.requireView().findViewById(R.id.cvChangePhotoProfile)
//            val cvChangeUsername: ListItemView = tabAppearanceFragment.requireView().findViewById(R.id.cvChangeUsername)
//            val cvHangeTheme: ListItemView = tabAppearanceFragment.requireView().findViewById(R.id.cvChangeTheme)
//
//            cvChangePhotoProfile.setOnClickListener {
//                // Aksi yang dijalankan saat cvChangePhotoProfile ditekan
//                // Contoh: Navigasi ke fragment GantiFotoProfileFragment
//            }
//
//            cvChangeUsername.setOnClickListener {
//                // Aksi yang dijalankan saat cvChangeUsername ditekan
//                // Contoh: Navigasi ke fragment GantiUsernameFragment
//            }
//
//            cvHangeTheme.setOnClickListener {
//                // Aksi yang dijalankan saat cvHangeTheme ditekan
//                // Contoh: Membuka dialog untuk memilih tema aplikasi
//            }
//        }


    }

    override fun onResume() {
        super.onResume()
        binding.bnvMainBottomNavigation.setupWithNavController(findNavController())
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