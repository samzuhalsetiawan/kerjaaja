package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeThemeViewModel @Inject constructor(
    private val preferences: DataStorePreferences
    ): ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }

}