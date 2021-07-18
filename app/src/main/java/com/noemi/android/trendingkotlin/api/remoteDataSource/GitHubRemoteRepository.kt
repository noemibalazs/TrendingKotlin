package com.noemi.android.trendingkotlin.api.remoteDataSource

import com.noemi.android.trendingkotlin.api.dataSource.GitHubAPI
import com.noemi.android.trendingkotlin.api.model.KotlinRepositories
import com.noemi.android.trendingkotlin.api.model.RepositoryDetails
import com.noemi.android.trendingkotlin.api.model.RepositoryReadMe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRemoteRepository @Inject constructor(private val gitHubAPI: GitHubAPI) : GitHubRemoteService {

    override fun getTrendingRepositories(): Single<KotlinRepositories> {
        return gitHubAPI.getTrendingRepositories()
    }

    override fun getRepositoryDetails(id: Int): Single<RepositoryDetails> {
        return gitHubAPI.getRepositoryDetails(id)
    }

    override fun getRepositoryReadMe(name: String, repo: String): Single<RepositoryReadMe> {
        return gitHubAPI.getRepositoryReadMe(name, repo)
    }
}