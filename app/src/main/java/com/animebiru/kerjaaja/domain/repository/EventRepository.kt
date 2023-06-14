package com.animebiru.kerjaaja.domain.repository

import com.animebiru.kerjaaja.domain.events.ProjectEvent
import com.animebiru.kerjaaja.domain.events.UIEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface EventRepository {

    fun getUIEventFlow(): SharedFlow<UIEvent>

    suspend fun emitUIEvent(event: UIEvent)

    fun getProjectEventFlow(): SharedFlow<ProjectEvent>

    suspend fun emitProjectEvent(event: ProjectEvent)

}