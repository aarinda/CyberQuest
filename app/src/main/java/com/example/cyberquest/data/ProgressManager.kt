package com.example.cyberquest.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.cyberquest.model.PlayerProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "player_progress"
private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

object ProgressKeys {
    val COMPLETED_LEVELS = stringSetPreferencesKey("completed_levels")
    val COINS = intPreferencesKey("coins")
    val BADGES = intPreferencesKey("badges")
}

class ProgressManager(private val context: Context) {
    val progressFlow: Flow<PlayerProgress> = context.dataStore.data.map { prefs ->
        PlayerProgress(
            completedLevels = prefs[ProgressKeys.COMPLETED_LEVELS] ?: emptySet(),
            coins = prefs[ProgressKeys.COINS] ?: 0,
            badges = prefs[ProgressKeys.BADGES] ?: 0
        )
    }

    suspend fun markLevelCompleted(levelId: String, coinsEarned: Int = 0, badgesEarned: Int = 0) {
        context.dataStore.edit { prefs ->
            val current = prefs[ProgressKeys.COMPLETED_LEVELS] ?: emptySet()
            prefs[ProgressKeys.COMPLETED_LEVELS] = current + levelId
            prefs[ProgressKeys.COINS] = (prefs[ProgressKeys.COINS] ?: 0) + coinsEarned
            prefs[ProgressKeys.BADGES] = (prefs[ProgressKeys.BADGES] ?: 0) + badgesEarned
        }
    }
}
