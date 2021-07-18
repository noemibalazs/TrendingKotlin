package com.noemi.android.trendingkotlin.api.dataSource

import com.noemi.android.trendingkotlin.api.model.*
import io.reactivex.Single
import retrofit2.http.*

interface GitHubAPI {

    @GET("search/repositories?q=created:>2021-07-15&q=language:kotlin&sort=starts&order=desc&per_page=100")
    fun getTrendingRepositories(): Single<KotlinRepositories>

    @GET("repositories/{id}")
    fun getRepositoryDetails(@Path("id") id: Int): Single<RepositoryDetails>

    @GET("repos/{name}/{repo}/readme")
    fun getRepositoryReadMe(
        @Path("name") name: String,
        @Path("repo") repo: String
    ): Single<RepositoryReadMe>
}