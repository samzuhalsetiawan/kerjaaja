package com.animebiru.kerjaaja.presentation.profile.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animebiru.kerjaaja.presentation.profile.appearance.theme.ChangeThemeViewModel
import com.animebiru.kerjaaja.presentation.profile.appearance.theme.SettingsThemePreferences

class ViewModelFactory(private val preferences: SettingsThemePreferences): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeThemeViewModel::class.java)) {
            return ChangeThemeViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}