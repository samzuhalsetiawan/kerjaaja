package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.events.AuthenticationEvent
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.sealed_class.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val dataStorePreferences: DataStorePreferences
): ViewModel() {

    private val _events: MediatorLiveData<AuthenticationEvent> = MediatorLiveData()
    val events: LiveData<AuthenticationEvent> = _events

    init {
        _events.addSource(dataStorePreferences.getAccessTokenFlow().asLiveData(viewModelScope.coroutineContext)) {
            _events.value = if (it != null) AuthenticationEvent.OnLogin else AuthenticationEvent.OnLogout
        }
    }

    fun login(username: String, password: String) {
        _events.value = AuthenticationEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.login(username, password)) {
                is Result.Success -> dataStorePreferences.setAccessToken(result.data)
                is Result.Error -> _events.postValue(AuthenticationEvent.OnError(result.exception.message ?: "Unhandled Error"))
            }
        }
    }

    fun register(username: String, fullName: String, gender: Gender, password: String) {
        _events.value = AuthenticationEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.register(username, fullName, gender, password)) {
                is Result.Success -> _events.postValue(AuthenticationEvent.OnRegisterSuccess(username))
                is Result.Error -> _events.postValue(AuthenticationEvent.OnError(result.exception.message ?: "Unhandled Error"))
            }
        }
    }

    fun logout() {
        _events.value = AuthenticationEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.logout()) {
                is Result.Error -> _events.postValue(AuthenticationEvent.OnError(result.exception.message ?: "Unhandled Error"))
                is Result.Success -> Unit
            }
        }
    }

    fun notifyTransactionSuccess() {
        _events.value = AuthenticationEvent.OnTransactionSuccess
    }

}