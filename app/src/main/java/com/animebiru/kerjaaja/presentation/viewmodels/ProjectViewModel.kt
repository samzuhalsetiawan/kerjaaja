package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.domain.enums.ProjectStatus
import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.events.UIEvent
import com.animebiru.kerjaaja.domain.repository.EventRepository
import com.animebiru.kerjaaja.domain.repository.ProjectCategoryRepository
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val _projectCategoryPager: Pager<Int, ProjectCategoryEntity>,
    private val _projectPager: Pager<Int, ProjectEntity>,
    private val projectCategoryRepository: ProjectCategoryRepository,
    private val projectRepository: ProjectRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    val projectCategoryPager = _projectCategoryPager.liveData.cachedIn(viewModelScope)
    val projectPager = _projectPager.liveData.cachedIn(viewModelScope)

    val projectEvent = eventRepository.getProjectEventFlow()

    val projectCategories = projectCategoryRepository.getProjectCategoriesStateFlow()

    fun createProject(
        description: String?,
        projectFee: String?,
        expire: String?,
        location: String?,
        projectCategories: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            eventRepository.emitUIEvent(UIEvent.OnLoading)
            description ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Deskripsi project tidak boleh kosong")); return@launch }
            expire ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Deadline tidak boleh kosong")); return@launch }
            val status = ProjectStatus.OPEN
            val fee = projectFee?.toDouble() ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Upah tidak boleh kosong")); return@launch }
            val latitude = location?.split(",")?.map { it.trim() }?.get(0)?.toFloatOrNull() ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Lokasi tidak valid")); return@launch }
            val longitude = location.split(",").map { it.trim() }[1].toFloatOrNull() ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Lokasi tidak valid")); return@launch }
            val categories = projectCategories?.split(",")?.filter { it.isNotBlank() }?.map { it.trim() } ?: run { eventRepository.emitUIEvent(UIEvent.OnError("Kategori tidak boleh kosong")); return@launch }
            when (val result = projectRepository.createProject(description, status, fee, expire, latitude, longitude, categories)) {
                is RepositoryResult.Error -> eventRepository.emitUIEvent(UIEvent.OnError(result.exception.message.toString()))
                is RepositoryResult.Success -> {
                    eventRepository.emitProjectEvent(ProjectEvent.OnNewProjectCreated)
                    eventRepository.emitUIEvent(UIEvent.OnComplete)
                }
            }
        }
    }

    fun searchProject(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            TODO("Search Project")
//            when (val result = projectRepository.)
        }
    }

}