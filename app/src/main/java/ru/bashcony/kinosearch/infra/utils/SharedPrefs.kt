package ru.bashcony.kinosearch.infra.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SharedPrefs(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "kinosearch_shared_preferences"
    }

}