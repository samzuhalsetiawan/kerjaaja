package com.animebiru.kerjaaja.presentation.profile.appearance.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.databinding.FragmentChangeThemeBinding
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.profile.appearance.ViewModelFactory


class ChangeThemeFragment : Fragment(R.layout.fragment_change_theme) {
    private val binding by viewBindings(FragmentChangeThemeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = SettingsThemePreferences.getInstance(requireActivity().datastore)
        val switchViewModel = ViewModelProvider(this, ViewModelFactory(preference)).get(
            ChangeThemeViewModel::class.java)
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