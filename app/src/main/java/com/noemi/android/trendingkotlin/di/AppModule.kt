package com.noemi.android.trendingkotlin.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.noemi.android.trendingkotlin.TRENDING_DB
import com.noemi.android.trendingkotlin.room.RepositoryDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideDataBase(): RepositoryDB = Room.databaseBuilder(
        application.applicationContext,
        RepositoryDB::class.java, TRENDING_DB
    ).build()

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences =
        application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
}