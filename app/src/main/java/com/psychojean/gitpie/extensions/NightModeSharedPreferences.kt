package com.psychojean.gitpie.extensions

import android.app.Activity
import android.content.Context
import com.psychojean.gitpie.R

fun Activity.isNightModeExistsIntoASharedPreferences() =
    this.getNightModeSharedPreferences()
        .getString(
            getString(R.string.night_mode_shared_preferences_code)
            , null) != null


fun Activity.addNightModeIntoASharedPreferences(nightMode: String) {
    val nightModeSharedPreferencesEditor = this.getNightModeSharedPreferences().edit()
    nightModeSharedPreferencesEditor.putString(getString(R.string.night_mode_shared_preferences_code), nightMode)
    nightModeSharedPreferencesEditor.apply()
}

fun Activity.getNightModeSharedPreferences() = this.getSharedPreferences(
    getString(R.string.night_mode_shared_preferences_name), Context.MODE_PRIVATE)

fun Activity.removeAllNightModeFromSharedPreferences() =
    this.getNightModeSharedPreferences().edit().also {
        it.putString(getString(R.string.night_mode_shared_preferences_code), null)
        it.apply()
    }

fun Activity.getNightMode(): String? {
    return this.getNightModeSharedPreferences()
        .getString(getString(R.string.night_mode_shared_preferences_code), null)
}