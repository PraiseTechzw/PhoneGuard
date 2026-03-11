package com.praisetechzw.phoneguard.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    private val USER_TOKEN = stringPreferencesKey("user_token")
    private val USER_ID = stringPreferencesKey("user_id")

    val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    val userToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_TOKEN]
    }

    suspend fun saveUserSession(userId: String, token: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[USER_TOKEN] = token
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_TOKEN)
        }
    }
}
