package com.animebiru.kerjaaja.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.domain.enums.Gender
import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.events.UIEvent
import com.animebiru.kerjaaja.domain.events.UserEvent
import com.animebiru.kerjaaja.domain.repository.EventRepository
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val dataStorePreferences: DataStorePreferences,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _events: MutableLiveData<UserEvent> = MutableLiveData(UserEvent.OnLoading)
    val events: LiveData<UserEvent> = _events

    val detailUser = userRepository.getDetailUserStateFlow()

    init {
        viewModelScope.launch {
            dataStorePreferences.getAccessTokenFlow().collect {
                _events.postValue(if (it != null) UserEvent.OnLogin else UserEvent.OnLogout)
            }
            dataStorePreferences.getUsernameFlow().collect {
                if (it == null) logout()
            }
        }
    }

    fun login(username: String, password: String) {
        _events.value = UserEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.login(username, password)) {
                is RepositoryResult.Success -> dataStorePreferences.setAccessToken(result.data)
                is RepositoryResult.Error -> _events.postValue(UserEvent.OnError(result.exception.message ?: "login Error"))
            }
        }
    }

    fun register(username: String, fullName: String, gender: Gender, password: String) {
        _events.value = UserEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.register(username, fullName, gender, password)) {
                is RepositoryResult.Success -> _events.postValue(UserEvent.OnRegisterSuccess(username))
                is RepositoryResult.Error -> _events.postValue(UserEvent.OnError(result.exception.message ?: "register Error"))
            }
        }
    }

    fun logout() {
        _events.value = UserEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.logout()) {
                is RepositoryResult.Error -> _events.postValue(UserEvent.OnError(result.exception.message ?: "Logout Error"))
                is RepositoryResult.Success -> Unit
            }
        }
    }

    fun changeUserProfilePicture(image: File) {
        _events.value = UserEvent.OnLoading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.updateUserProfilePicture(image)) {
                is RepositoryResult.Success -> {
                    _events.postValue(UserEvent.OnChangePhotoProfileSuccess(image))
                }
                is RepositoryResult.Error -> _events.postValue(UserEvent.OnError(result.exception.message ?: "changeUserProfilePicture Error"))
            }
        }
    }

    fun getDetailUser() {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.emitUIEvent(UIEvent.OnLoading)
            when (val result = userRepository.getDetailUser()) {
                is RepositoryResult.Error -> eventRepository.emitUIEvent(UIEvent.OnError(result.exception.message.toString()))
                is RepositoryResult.Success -> eventRepository.emitUIEvent(UIEvent.OnComplete)
            }
        }
    }

    fun notifyTransactionSuccess() {
        _events.value = UserEvent.OnIdle
    }

}