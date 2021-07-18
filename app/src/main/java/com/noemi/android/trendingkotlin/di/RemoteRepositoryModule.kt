package com.noemi.android.trendingkotlin.di

import com.noemi.android.trendingkotlin.api.dataSource.GitHubAPI
import com.noemi.android.trendingkotlin.api.remoteDataSource.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteRepositoryModule {

    @Provides
    @Singleton
    fun providesRemoteRepository(gitHubAPI: GitHubAPI): GitHubRemoteService =
        GitHubRemoteRepository(gitHubAPI)
}