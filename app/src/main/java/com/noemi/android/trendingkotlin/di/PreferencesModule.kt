package com.noemi.android.trendingkotlin.di

import android.content.SharedPreferences
import com.noemi.android.trendingkotlin.preference.PreferenceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferenceRepository(sharedPreferences: SharedPreferences) = PreferenceRepository(sharedPreferences)
}