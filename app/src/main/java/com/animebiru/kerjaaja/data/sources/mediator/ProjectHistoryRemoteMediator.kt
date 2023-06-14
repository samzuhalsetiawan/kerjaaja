package com.animebiru.kerjaaja.data.sources.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.animebiru.kerjaaja.data.sources.local.MainDatabase
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import com.animebiru.kerjaaja.domain.sealed_class.RepositoryResult
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toEntity
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.toProjectHistory
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ProjectHistoryRemoteMediator @Inject constructor(
    private val mainDatabase: MainDatabase,
    private val projectRepository: ProjectRepository,
    private val dataStorePreferences: DataStorePreferences
): RemoteMediator<Int, HistoryProjectEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HistoryProjectEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> (state.lastItemOrNull()?.page ?: 0) + 1
            }
            val owner = dataStorePreferences.getUsernameFlow().first { it != null } ?: throw NullPointerException("Username null")
            val projects = when (val result = projectRepository.getProjectsByOwner(owner, page, state.config.pageSize)) {
                is RepositoryResult.Success -> result.data.map { it.toEntity(page) }
                is RepositoryResult.Error -> return MediatorResult.Error(result.exception)
            }
            mainDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) mainDatabase.dao.clearProjectsHistoryTable()
                mainDatabase.dao.upsertProjectsHistory(projects.map { it.toProjectHistory() })
            }
            MediatorResult.Success(projects.isEmpty())
        } catch (ioException: IOException) {
            MediatorResult.Error(ioException)
        } catch (httpException: HttpException) {
            MediatorResult.Error(httpException)
        } catch (tr: Throwable) {
            MediatorResult.Error(tr)
        }
    }
}