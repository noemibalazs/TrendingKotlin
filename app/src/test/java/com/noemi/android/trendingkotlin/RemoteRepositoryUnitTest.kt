package com.noemi.android.trendingkotlin

import com.noemi.android.trendingkotlin.api.dataSource.GitHubAPI
import com.noemi.android.trendingkotlin.api.model.KotlinRepositories
import com.noemi.android.trendingkotlin.api.remoteDataSource.GitHubRemoteRepository
import org.junit.Before
import org.junit.Test
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.noemi.android.trendingkotlin.api.model.RepositoryDetails
import com.noemi.android.trendingkotlin.api.model.RepositoryReadMe
import io.reactivex.Single

class RemoteRepositoryUnitTest {

    private lateinit var failure: Throwable
    private var gitHubAPI: GitHubAPI = mock()
    private lateinit var gitHubRemoteRepository: GitHubRemoteRepository

    @Before
    fun setUp() {
        gitHubRemoteRepository = GitHubRemoteRepository(gitHubAPI)
        failure = Throwable("Try it again, an error has occurred.")
    }

    @Test
    fun testTrendingRepositoriesShouldPass() {
        val result = mock<KotlinRepositories>()
        whenever(gitHubRemoteRepository.getTrendingRepositories()).thenReturn(Single.just(result))
        gitHubRemoteRepository.getTrendingRepositories().test().assertComplete()
        verify(gitHubAPI).getTrendingRepositories()
    }

    @Test
    fun testTrendingRepositoriesError() {
        whenever(gitHubRemoteRepository.getTrendingRepositories()).thenReturn(Single.error(failure))
        gitHubRemoteRepository.getTrendingRepositories().test().assertError(failure)
        verify(gitHubAPI).getTrendingRepositories()
    }

    @Test
    fun testRepositoryDetailsShouldPass() {
        val result = mock<RepositoryDetails>()
        whenever(gitHubRemoteRepository.getRepositoryDetails(12)).thenReturn(Single.just(result))
        gitHubRemoteRepository.getRepositoryDetails(12).test().assertComplete()
        verify(gitHubAPI).getRepositoryDetails(12)
    }

    @Test
    fun testRepositoryDetailsError() {
        whenever(gitHubRemoteRepository.getRepositoryDetails(12)).thenReturn(Single.error(failure))
        gitHubRemoteRepository.getRepositoryDetails(12).test().assertError(failure)
        verify(gitHubAPI).getRepositoryDetails(12)
    }

    @Test
    fun testRepositoryReadMeShouldPass() {
        val result = mock<RepositoryReadMe>()
        whenever(gitHubRemoteRepository.getRepositoryReadMe("kotlin", "x")).thenReturn(
            Single.just(
                result
            )
        )
        gitHubRemoteRepository.getRepositoryReadMe("kotlin", "x").test().assertComplete()
        verify(gitHubAPI).getRepositoryReadMe("kotlin", "x")
    }

    @Test
    fun testRepositoryReadMeError() {
        whenever(gitHubRemoteRepository.getRepositoryReadMe("kotlin", "x")).thenReturn(
            Single.error(
                failure
            )
        )
        gitHubRemoteRepository.getRepositoryReadMe("kotlin", "x").test().assertError(failure)
        verify(gitHubAPI).getRepositoryReadMe("kotlin", "x")
    }
}