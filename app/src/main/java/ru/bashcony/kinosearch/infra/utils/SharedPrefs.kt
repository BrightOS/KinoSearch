package ru.bashcony.kinosearch.infra.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SharedPrefs(private val sharedPreferences: SharedPreferences) {

    // Night mode repository

    var token: String
        get() = sharedPreferences.getString(PREFERENCE_TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit().putString(PREFERENCE_TOKEN, value).apply()
        }

    companion object {
        private const val PREFERENCE_TOKEN = "kinosearch_preference_token"
    }

}