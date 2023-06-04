package com.animebiru.kerjaaja.data.sources.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(private val applicationContext: Context) {

    private val Context.preferences: DataStore<Preferences> by preferencesDataStore(DATASTORE_PREFERENCES_NAME)

    private val accessTokenKey by lazy { stringPreferencesKey(DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY) }

    suspend fun setAccessToken(token: String) {
        applicationContext.preferences.edit { mutablePreferences ->
            mutablePreferences[accessTokenKey] = token
        }
    }

    suspend fun deleteAccessToken() {
        applicationContext.preferences.edit { mutablePreferences ->
            mutablePreferences.remove(accessTokenKey)
        }
    }

    fun getAccessTokenFlow(): Flow<String?> {
        return applicationContext.preferences.data.map { preferences ->
            preferences[accessTokenKey]
        }
    }

    companion object {
        private const val DATASTORE_PREFERENCES_NAME = "com.animebiru.kerjaaja.DATASTORE_PREFERENCES_NAME"
        private const val DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY = "com.animebiru.kerjaaja.DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY"
    }
}