package com.animebiru.kerjaaja.data.sources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity

@Dao
abstract class MainDao {

    @Upsert(entity = ProjectEntity::class)
    abstract suspend fun upsertProject(project: ProjectEntity)

    @Upsert(entity = ProjectEntity::class)
    abstract suspend fun upsertProjects(projects: List<ProjectEntity>)

    @Query("SELECT * FROM projects")
    abstract fun getProjects(): PagingSource<Int, ProjectEntity>

    @Query("DELETE FROM projects")
    abstract suspend fun clearProjectsTable()

    @Upsert(entity = HistoryProjectEntity::class)
    abstract suspend fun upsertProjectHistory(project: HistoryProjectEntity)

    @Upsert(entity = HistoryProjectEntity::class)
    abstract suspend fun upsertProjectsHistory(projects: List<HistoryProjectEntity>)

    @Query("SELECT * FROM projects_history")
    abstract fun getProjectsHistory(): PagingSource<Int, HistoryProjectEntity>

    @Query("DELETE FROM projects_history")
    abstract suspend fun clearProjectsHistoryTable()

    @Upsert(entity = ProjectCategoryEntity::class)
    abstract suspend fun upsertProjectCategory(projectCategory: ProjectCategoryEntity)

    @Upsert(entity = ProjectCategoryEntity::class)
    abstract suspend fun upsertProjectCategories(projectCategories: List<ProjectCategoryEntity>)

    @Query("SELECT * FROM project_categories ORDER BY created_at DESC")
    abstract fun getProjectCategories(): PagingSource<Int, ProjectCategoryEntity>

    @Query("DELETE FROM project_categories")
    abstract suspend fun clearProjectCategoriesTable()

}