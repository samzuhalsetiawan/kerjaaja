package com.animebiru.kerjaaja.presentation.viewmodels

import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animebiru.kerjaaja.domain.events.UIEvent
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.repository.EventRepository
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import com.animebiru.kerjaaja.domain.repository.UserRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _detailProject: MutableStateFlow<Project?> = MutableStateFlow(null)
    val detailProject = _detailProject.asStateFlow()

    private val _projectLocation: MutableStateFlow<String?> = MutableStateFlow(null)
    val projectLocation = _projectLocation.asStateFlow()

    fun getDetailProject(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.emitUIEvent(UIEvent.OnLoading)
            when (val result = projectRepository.getProjectById(projectId)) {
                is RepositoryResult.Error -> eventRepository.emitUIEvent(UIEvent.OnError(result.exception.message.toString()))
                is RepositoryResult.Success -> {
                    _detailProject.value = result.data
                    eventRepository.emitUIEvent(UIEvent.OnComplete)
                }
            }
        }
    }

    fun getLocationName(latLng: LatLng, geocoder: Geocoder) {
        getLocationName(latLng.latitude, latLng.longitude, geocoder)
    }

    fun getLocationName(latitude: Double, longitude: Double, geocoder: Geocoder) {
        viewModelScope.launch(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) {
                    _projectLocation.value = it[0].locality
                }
            } else {
                try {
                    geocoder.getFromLocation(latitude, longitude, 1)?.let {
                        _projectLocation.value = it[0].locality
                    }
                } catch (err: IOException) {
                    _projectLocation.value = "Unknown"
                }
            }
        }
    }

}