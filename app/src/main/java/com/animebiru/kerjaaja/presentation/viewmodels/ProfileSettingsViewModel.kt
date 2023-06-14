package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(): ViewModel() {

    private val _currentSelectedPhotoProfile: MutableLiveData<File>  = MutableLiveData()
    val currentSelectedPhotoProfile: LiveData<File> = _currentSelectedPhotoProfile

    fun setCurrentSelectedPhotoProfile(image: File) {
        _currentSelectedPhotoProfile.value = image
    }
}