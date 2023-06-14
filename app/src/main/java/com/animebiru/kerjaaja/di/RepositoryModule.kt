package com.animebiru.kerjaaja.di

import com.animebiru.kerjaaja.data.repository.EventRepositoryImpl
import com.animebiru.kerjaaja.data.repository.ProjectCategoryRepositoryImpl
import com.animebiru.kerjaaja.data.repository.ProjectRepositoryImpl
import com.animebiru.kerjaaja.data.repository.UserRepositoryImpl
import com.animebiru.kerjaaja.domain.repository.EventRepository
import com.animebiru.kerjaaja.domain.repository.ProjectCategoryRepository
import com.animebiru.kerjaaja.domain.repository.ProjectRepository
import com.animebiru.kerjaaja.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindProjectRepository(
        projectRepositoryImpl: ProjectRepositoryImpl
    ): ProjectRepository

    @Binds
    @Singleton
    abstract fun bindProjectCategoryRepository(
        projectCategoryRepositoryImpl: ProjectCategoryRepositoryImpl
    ): ProjectCategoryRepository

    @Binds
    @Singleton
    abstract fun provideUIEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository

}