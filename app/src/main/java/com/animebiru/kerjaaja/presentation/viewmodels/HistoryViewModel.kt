package com.animebiru.kerjaaja.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val projectHistoryPager: Pager<Int, HistoryProjectEntity>
): ViewModel() {

    val historyPager = projectHistoryPager.liveData.cachedIn(viewModelScope)

}