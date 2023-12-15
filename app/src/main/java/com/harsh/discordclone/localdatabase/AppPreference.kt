package com.harsh.discordclone.localdatabase

import android.content.Context

class AppPreference(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var lastSynced: Long
        get() = sharedPreferences.getLong(LAST_SYNC_TIMESTAMP, 0)
        set(time: Long) = sharedPreferences.edit().putLong(LAST_SYNC_TIMESTAMP, time).apply()

    companion object {
        private const val PREFS_NAME = "DiscordAppPreference"
        private const val LAST_SYNC_TIMESTAMP = "last_synced"
    }
}