package com.animebiru.kerjaaja.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentProfileBinding
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.presentation.adapter.ProfileSectionsPagerAdapter
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.custom_view.ListItemView
import com.animebiru.kerjaaja.presentation.viewmodels.ProjectViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBindings(FragmentProfileBinding::bind)
    private val projectViewModel: ProjectViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.homeFragment, R.id.historyFragment, R.id.profileFragment)) }
    private val profileSectionsPagerAdapter by lazy { ProfileSectionsPagerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getDetailUser()
        binding.ctlProfileFragment.setupWithNavController(binding.mtbProfileFragment, findNavController(), appBarConfiguration)
        val viewPager: ViewPager2 = binding.vp2ProfilePage
        val tabs: TabLayout = binding.tlProfilePage
        viewPager.adapter = profileSectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        collectLatestFlowWhenStarted(userViewModel.detailUser) {
            val user = it ?: return@collectLatestFlowWhenStarted
            binding.tvUsername.text = user.fullName
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.ivProfilePicture)
        }

        projectViewModel.projectPager.observe(viewLifecycleOwner) { profileSectionsPagerAdapter.onProjectPagerDataChange(it) }

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