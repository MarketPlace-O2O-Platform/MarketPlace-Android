package dev.kichan.marketplace.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth")
private val AUTH_TOKEN = stringPreferencesKey("authToken")

suspend fun saveAuthToken(context: Context, token: String) {
    context.dataStore.edit { preferences ->
        preferences[AUTH_TOKEN] = token
    }
}

suspend fun removeAuthToken(context: Context) {
    context.dataStore.edit { preferences ->
        preferences[AUTH_TOKEN] = ""
    }
}

fun getAuthToken(context: Context) : Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[AUTH_TOKEN]
}