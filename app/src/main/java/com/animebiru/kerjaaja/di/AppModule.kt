package com.animebiru.kerjaaja.di

import android.app.Application
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.data.sources.remote.ApiConfig
import com.animebiru.kerjaaja.data.sources.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

}