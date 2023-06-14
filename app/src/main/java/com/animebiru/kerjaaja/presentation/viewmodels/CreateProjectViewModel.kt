package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(): ViewModel() {

    private val _currentDescriptionInput: MutableLiveData<String> = MutableLiveData()
    val currentDescriptionInput: LiveData<String> = _currentDescriptionInput

    private val _currentLocationInput: MutableLiveData<String> = MutableLiveData()
    val currentLocationInput: LiveData<String> = _currentLocationInput

    private val _currentDateInput: MutableLiveData<String> = MutableLiveData()
    val currentDateInput: LiveData<String> = _currentDateInput

    private val _currentCategoryInput: MutableLiveData<String> = MutableLiveData()
    val currentCategoryInput: LiveData<String> = _currentCategoryInput

    fun setCurrentDescriptionInput(input: String) {
        _currentDescriptionInput.value = input
    }
    fun setCurrentLocationInput(input: String) {
        _currentLocationInput.value = input
    }
    fun setCurrentDateInput(input: String) {
        _currentDateInput.value = input
    }
    fun setCurrentCategoryInput(input: String) {
        _currentDescriptionInput.value = input
    }
}