package com.animebiru.kerjaaja.domain.repository

import com.animebiru.kerjaaja.domain.enums.ProjectStatus
import com.animebiru.kerjaaja.domain.models.Project
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult

interface ProjectRepository {

    suspend fun getProjects(page: Int = 0, size: Int = 10): RepositoryResult<List<Project>>

    suspend fun getProjectsByOwner(owner: String, page: Int = 0, size: Int = 10): RepositoryResult<List<Project>>

    suspend fun createProject(
        title: String,
        status: ProjectStatus,
        fee: Double,
        deadline: String,
        latitude: Float,
        longitude: Float,
        categories: List<String>
    ): RepositoryResult<Unit>

    suspend fun getProjectById(projectId: String, page: Int = 0, size: Int = 1): RepositoryResult<Project>

    suspend fun getUserCreatedProjects(page: Int = 0, size: Int = 10): RepositoryResult<List<Project>>
}