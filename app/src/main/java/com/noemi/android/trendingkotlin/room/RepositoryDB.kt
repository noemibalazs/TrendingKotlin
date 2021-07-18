package com.noemi.android.trendingkotlin.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepositoryEntity::class], version = 1, exportSchema = false)
abstract class RepositoryDB : RoomDatabase() {

    abstract fun getRepositoryDAO(): RepositoryDAO
}