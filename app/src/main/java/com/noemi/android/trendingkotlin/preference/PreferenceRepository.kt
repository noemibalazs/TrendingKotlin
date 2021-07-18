package com.noemi.android.trendingkotlin.preference

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var repositoryID: Int
        set(value) {
            sharedPreferences.edit().putInt(KEY_REPOSITORY_ID, value).apply()
        }
        get() = sharedPreferences.getInt(KEY_REPOSITORY_ID, 0)

    var repositoryFullName: String
        set(value) {
            sharedPreferences.edit().putString(KEY_REPOSITORY_FULL_NAME, value).apply()
        }
        get() = sharedPreferences.getString(KEY_REPOSITORY_FULL_NAME, "") ?: ""

    companion object {
        private const val KEY_REPOSITORY_ID = "key_repository_id"
        private const val KEY_REPOSITORY_FULL_NAME = "key_repository_full_name"
    }
}