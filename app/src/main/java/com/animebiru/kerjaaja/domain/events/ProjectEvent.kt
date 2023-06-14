package com.animebiru.kerjaaja.domain.events

sealed class ProjectEvent private constructor(){
    object OnRefreshProject: ProjectEvent()
    object OnNewProjectCreated: ProjectEvent()
}