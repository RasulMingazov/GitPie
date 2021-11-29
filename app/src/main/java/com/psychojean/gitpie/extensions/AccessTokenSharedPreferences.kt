package com.psychojean.gitpie.extensions

import android.app.Activity
import android.content.Context
import com.psychojean.gitpie.R

    fun Activity.isAccessTokenExistsIntoASharedPreferences() =
         this.getAccessTokenSharedPreferences()
             .getString(
                 getString(R.string.access_token_shared_preferences_code)
                 , null) != null


    fun Activity.addAccessTokenIntoASharedPreferences(accessToken: String) {
        val accessTokenSharedPreferencesEditor = this.getAccessTokenSharedPreferences().edit()
        accessTokenSharedPreferencesEditor.putString(getString(R.string.access_token_shared_preferences_code), accessToken)
        accessTokenSharedPreferencesEditor.apply()
    }

    fun Activity.getAccessTokenSharedPreferences() = this.getSharedPreferences(
        getString(R.string.access_token_shared_preferences_name), Context.MODE_PRIVATE)

    fun Activity.removeAllAccessTokensFromSharedPreferences() =
        this.getAccessTokenSharedPreferences().edit().also {
            it.putString(getString(R.string.access_token_shared_preferences_code), null)
            it.apply()
    }

    fun Activity.getAccessToken(): String {
        return if (this.isAccessTokenExistsIntoASharedPreferences()) {
            this.getAccessTokenSharedPreferences().getString(getString(R.string.access_token_shared_preferences_code), null)!!
        } else {
            "Mistake"
        }
    }


