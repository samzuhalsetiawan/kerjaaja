package com.animebiru.kerjaaja.data.sources.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(private val applicationContext: Context) {

    private val Context.tokenPreferences: DataStore<Preferences> by preferencesDataStore(DATASTORE_TOKEN_PREFERENCES_NAME)
    private val Context.themePreferences: DataStore<Preferences> by preferencesDataStore(DATASTORE_THEME_PREFERENCES_NAME)

    private val accessTokenKey by lazy { stringPreferencesKey(DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY) }
    private val themeKey by lazy { booleanPreferencesKey(DATASTORE_PREFERENCES_THEME_KEY) }

    suspend fun setAccessToken(token: String) {
        applicationContext.tokenPreferences.edit { mutablePreferences ->
            mutablePreferences[accessTokenKey] = token
        }
    }

    suspend fun deleteAccessToken() {
        applicationContext.tokenPreferences.edit { mutablePreferences ->
            mutablePreferences.remove(accessTokenKey)
        }
    }

    fun getAccessTokenFlow(): Flow<String?> {
        return applicationContext.tokenPreferences.data.map { preferences ->
            preferences[accessTokenKey]
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return applicationContext.themePreferences.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        applicationContext.themePreferences.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        private const val DATASTORE_TOKEN_PREFERENCES_NAME = "com.animebiru.kerjaaja.DATASTORE_TOKEN_PREFERENCES_NAME"
        private const val DATASTORE_THEME_PREFERENCES_NAME = "com.animebiru.kerjaaja.DATASTORE_THEME_PREFERENCES_NAME"
        private const val DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY = "com.animebiru.kerjaaja.DATASTORE_PREFERENCES_ACCESS_TOKEN_KEY"
        private const val DATASTORE_PREFERENCES_THEME_KEY = "com.animebiru.kerjaaja.DATASTORE_PREFERENCES_THEME_KEY"
    }
}