package com.noemi.android.trendingkotlin.di

import com.noemi.android.trendingkotlin.api.localDataSource.GitHubLocalService
import com.noemi.android.trendingkotlin.api.remoteDataSource.GitHubRemoteService
import com.noemi.android.trendingkotlin.viewModel.RepositoryViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideRepositoryViewModel(
        gitHubRemoteService: GitHubRemoteService,
        gitHubLocalService: GitHubLocalService
    ) =
        RepositoryViewModel.Factory(gitHubRemoteService, gitHubLocalService)
}