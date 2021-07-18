package com.noemi.android.trendingkotlin.di

import com.noemi.android.trendingkotlin.api.localDataSource.*
import com.noemi.android.trendingkotlin.room.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalRepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryDAO(repositoryDB: RepositoryDB): RepositoryDAO =
        repositoryDB.getRepositoryDAO()

    @Provides
    @Singleton
    fun provideLocalRepository(repositoryDAO: RepositoryDAO): GitHubLocalService =
        GitHubLocalRepository(repositoryDAO)
}