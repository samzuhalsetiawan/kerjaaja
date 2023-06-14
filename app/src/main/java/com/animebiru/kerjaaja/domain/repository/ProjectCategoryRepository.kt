package com.animebiru.kerjaaja.domain.repository

import com.animebiru.kerjaaja.domain.models.ProjectCategory
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ProjectCategoryRepository {

    fun getProjectCategoriesStateFlow(): StateFlow<List<ProjectCategory>>

    suspend fun getProjectCategories(page: Int = 0, size: Int = 10): RepositoryResult<List<ProjectCategory>>

}