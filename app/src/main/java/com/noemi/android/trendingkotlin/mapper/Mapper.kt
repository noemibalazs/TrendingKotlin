package com.noemi.android.trendingkotlin.mapper

import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.room.RepositoryEntity
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapRepositoryToEntity(repository: Repository): RepositoryEntity {
        return RepositoryEntity(
            id = repository.id,
            name = repository.name,
            fullName = repository.fullName,
            description = repository.description ?: "",
            updated = repository.updated,
            stargazers = repository.stargazersCount
        )
    }

    fun mapRepositoryEntityToRepository(entity: RepositoryEntity): Repository {
        return Repository(
            id = entity.id,
            name = entity.name,
            fullName = entity.fullName,
            description = entity.description,
            updated = entity.updated,
            stargazersCount = entity.stargazers
        )
    }
}