package com.animebiru.kerjaaja.data.sources.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.animebiru.kerjaaja.data.sources.local.MainDatabase
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.domain.repository.ProjectCategoryRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toEntity
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ProjectCategoryRemoteMediator @Inject constructor(
    private val mainDatabase: MainDatabase,
    private val projectCategoryRepository: ProjectCategoryRepository
): RemoteMediator<Int, ProjectCategoryEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProjectCategoryEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> (state.lastItemOrNull()?.page ?: 0) + 1
            }
            val projectCategories = when (val result = projectCategoryRepository.getProjectCategories(page, state.config.pageSize)) {
                is RepositoryResult.Success -> result.data.map { it.toEntity(page) }
                is RepositoryResult.Error -> return MediatorResult.Error(result.exception)
            }
            mainDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) mainDatabase.dao.clearProjectCategoriesTable()
                mainDatabase.dao.upsertProjectCategories(projectCategories)
            }
            MediatorResult.Success(projectCategories.isEmpty())
        } catch (ioException: IOException) {
            MediatorResult.Error(ioException)
        } catch (httpException: HttpException) {
            MediatorResult.Error(httpException)
        } catch (tr: Throwable) {
            MediatorResult.Error(tr)
        }
    }
}