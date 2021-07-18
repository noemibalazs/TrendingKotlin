package com.noemi.android.trendingkotlin.api.remoteDataSource

import com.noemi.android.trendingkotlin.api.model.KotlinRepositories
import com.noemi.android.trendingkotlin.api.model.RepositoryDetails
import com.noemi.android.trendingkotlin.api.model.RepositoryReadMe
import io.reactivex.Single

interface GitHubRemoteService {

    fun getTrendingRepositories(): Single<KotlinRepositories>

    fun getRepositoryDetails(id: Int): Single<RepositoryDetails>

    fun getRepositoryReadMe(name: String, repo: String): Single<RepositoryReadMe>
}