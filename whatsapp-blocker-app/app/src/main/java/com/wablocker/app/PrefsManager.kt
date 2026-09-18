package com.wablocker.app

import android.content.Context

object PrefsManager {
    private const val PREFS_NAME = "wablocker_prefs"
    private const val KEY_BLOCKING_ENABLED = "blocking_enabled"

    fun isBlockingEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_BLOCKING_ENABLED, false)
    }

    fun setBlockingEnabled(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_BLOCKING_ENABLED, enabled).apply()
    }
}
