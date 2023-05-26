package com.animebiru.kerjaaja.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.animebiru.kerjaaja.domain.sealed_class.MainActivityEvents

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val _events: MutableLiveData<MainActivityEvents> = MutableLiveData()
    val events: LiveData<MainActivityEvents> = _events

    fun logout() {
        TODO()
    }
}