package com.animebiru.kerjaaja.data.repository

import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.events.UIEvent
import com.animebiru.kerjaaja.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(): EventRepository {

    private val uiEvent: MutableSharedFlow<UIEvent> = MutableSharedFlow()

    private val projectEvent: MutableSharedFlow<ProjectEvent> = MutableSharedFlow(replay = 1)

    override fun getUIEventFlow(): SharedFlow<UIEvent> {
        return uiEvent.asSharedFlow()
    }

    override suspend fun emitUIEvent(event: UIEvent) {
        uiEvent.emit(event)
    }

    override fun getProjectEventFlow(): SharedFlow<ProjectEvent> {
        return projectEvent.asSharedFlow()
    }

    override suspend fun emitProjectEvent(event: ProjectEvent) {
        projectEvent.emit(event)
    }
}