package com.animebiru.kerjaaja.presentation.ui.profile.appearance.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangeThemeBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.viewmodels.ChangeThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeThemeFragment : Fragment(R.layout.fragment_change_theme) {
    private val binding by viewBindings(FragmentChangeThemeBinding::bind)
    private val switchViewModel: ChangeThemeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.changeTheme.isChecked = false
            }
        }

        binding.changeTheme.setOnCheckedChangeListener { _:CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)

        }
    }


    companion object {
    }
}