package com.animebiru.kerjaaja.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import androidx.room.RoomDatabase
import com.animebiru.kerjaaja.data.sources.local.MainDatabase
import com.animebiru.kerjaaja.data.sources.local.entity.HistoryProjectEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectCategoryEntity
import com.animebiru.kerjaaja.data.sources.local.entity.ProjectEntity
import com.animebiru.kerjaaja.data.sources.mediator.ProjectCategoryRemoteMediator
import com.animebiru.kerjaaja.data.sources.mediator.ProjectHistoryRemoteMediator
import com.animebiru.kerjaaja.data.sources.mediator.ProjectRemoteMediator
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.data.sources.remote.ApiConfig
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(application: Application): DataStorePreferences {
        return DataStorePreferences(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideMainApi(dataStorePreferences: DataStorePreferences): ApiService {
        return ApiConfig(dataStorePreferences).api
    }

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(application.applicationContext, MainDatabase::class.java, "kerjaaja_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideProjectPager(
        projectRemoteMediator: ProjectRemoteMediator,
        mainDatabase: MainDatabase
    ): Pager<Int, ProjectEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = projectRemoteMediator,
            pagingSourceFactory = { mainDatabase.dao.getProjects() }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideProjectHistoryPager(
        projectRemoteMediator: ProjectHistoryRemoteMediator,
        mainDatabase: MainDatabase
    ): Pager<Int, HistoryProjectEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = projectRemoteMediator,
            pagingSourceFactory = { mainDatabase.dao.getProjectsHistory() }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideProjectCategoryPager(
        projectCategoryRemoteMediator: ProjectCategoryRemoteMediator,
        mainDatabase: MainDatabase
    ): Pager<Int, ProjectCategoryEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = projectCategoryRemoteMediator,
            pagingSourceFactory = { mainDatabase.dao.getProjectCategories() }
        )
    }
}