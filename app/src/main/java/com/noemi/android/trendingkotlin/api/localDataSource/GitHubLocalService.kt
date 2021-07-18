package com.noemi.android.trendingkotlin.api.localDataSource

import com.noemi.android.trendingkotlin.room.RepositoryEntity

interface GitHubLocalService {

    fun insertRepositoryList(entityList: MutableList<RepositoryEntity>)

    fun getFilteredRepositoryList(stars: Int): MutableList<RepositoryEntity>
}