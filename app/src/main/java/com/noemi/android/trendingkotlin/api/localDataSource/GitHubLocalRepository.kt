package com.noemi.android.trendingkotlin.api.localDataSource

import com.noemi.android.trendingkotlin.room.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubLocalRepository @Inject constructor(private val repositoryDAO: RepositoryDAO) :
    GitHubLocalService {

    override fun insertRepositoryList(entityList: MutableList<RepositoryEntity>) {
        return repositoryDAO.insertRepositoryList(entityList)
    }

    override fun getFilteredRepositoryList(stars: Int): MutableList<RepositoryEntity> {
        return repositoryDAO.getFilteredRepositoryList(stars)
    }
}