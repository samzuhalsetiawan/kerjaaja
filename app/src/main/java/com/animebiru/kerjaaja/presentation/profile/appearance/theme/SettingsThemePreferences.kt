package com.animebiru.kerjaaja.presentation.profile.appearance.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences>  by preferencesDataStore(name = "settings")

class SettingsThemePreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }
    companion object{
        @Volatile
        private var INSTANCE: SettingsThemePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsThemePreferences {
            return INSTANCE ?: synchronized(this){
                val instance = SettingsThemePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}