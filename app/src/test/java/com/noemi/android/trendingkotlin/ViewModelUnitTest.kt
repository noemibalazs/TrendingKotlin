package com.noemi.android.trendingkotlin

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.noemi.android.trendingkotlin.api.localDataSource.GitHubLocalService
import com.noemi.android.trendingkotlin.api.model.*
import com.noemi.android.trendingkotlin.api.remoteDataSource.GitHubRemoteService
import com.noemi.android.trendingkotlin.mapper.Mapper
import com.noemi.android.trendingkotlin.viewModel.RepositoryViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ViewModelUnitTest {

    private lateinit var repositoryViewModel: RepositoryViewModel
    private val gitHubRemoteService: GitHubRemoteService = mock()
    private val gitHubLocalService: GitHubLocalService = mock()
    private val progressObserver: Observer<Boolean> = mock()
    private val errorObserver: Observer<String> = mock()
    private val mapper: Mapper = mock()
    private lateinit var failure: Throwable
    private val trendingRepositoriesObserver: Observer<MutableList<Repository>> = mock()
    private val repositoryDetailsObserver: Observer<RepositoryDetails> = mock()
    private val repositoryReadMeObserver: Observer<RepositoryReadMe> = mock()

    @Before
    fun setUp() {
        repositoryViewModel = RepositoryViewModel(gitHubRemoteService, gitHubLocalService)
        failure = Throwable("An error has occurred, try it again!")
    }

    @Test(expected = NullPointerException::class)
    fun testGetTrendingRepositoriesSuccess() {
        val result = mock<KotlinRepositories>()
        whenever(gitHubRemoteService.getTrendingRepositories()).thenReturn(Single.just(result))
        repositoryViewModel.loadTrendingRepositories(mapper)
        repositoryViewModel.trendingRepositories.observeForever(trendingRepositoriesObserver)

        verify(progressObserver).onChanged(true)
        verify(trendingRepositoriesObserver).onChanged(result.items)
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testGetTrendingRepositoriesError() {
        whenever(gitHubRemoteService.getTrendingRepositories()).thenReturn(Single.error(failure))
        repositoryViewModel.loadTrendingRepositories(mapper)
        repositoryViewModel.trendingRepositories.observeForever(trendingRepositoriesObserver)

        val captor = argumentCaptor<String>()
        verify(progressObserver).onChanged(true)
        verify(trendingRepositoriesObserver).onChanged(null)
        verify(errorObserver).onChanged(captor.capture())
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryDetailsSuccess() {
        val result = mock<RepositoryDetails>()
        whenever(gitHubRemoteService.getRepositoryDetails(12)).thenReturn(Single.just(result))
        repositoryViewModel.loadRepositoryDetails(12)
        repositoryViewModel.repositoryDetails.observeForever(repositoryDetailsObserver)

        verify(progressObserver).onChanged(true)
        verify(repositoryDetailsObserver).onChanged(result)
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryDetailsError() {
        whenever(gitHubRemoteService.getRepositoryDetails(12)).thenReturn(Single.error(failure))
        repositoryViewModel.loadRepositoryDetails(12)
        repositoryViewModel.repositoryDetails.observeForever(repositoryDetailsObserver)

        val capture = argumentCaptor<String>()

        verify(progressObserver).onChanged(true)
        verify(repositoryDetailsObserver).onChanged(null)
        verify(errorObserver).onChanged(capture.capture())
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryReadMeSuccess() {
        val result = mock<RepositoryReadMe>()
        whenever(gitHubRemoteService.getRepositoryReadMe("kotlin", "x")).thenReturn(
            Single.just(
                result
            )
        )
        repositoryViewModel.loadRepositoryReadMe("kotlin", "x")
        repositoryViewModel.repositoryReadMe.observeForever(repositoryReadMeObserver)

        verify(progressObserver).onChanged(true)
        verify(repositoryReadMeObserver).onChanged(result)
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryReadMeError() {
        whenever(gitHubRemoteService.getRepositoryReadMe("kotlin", "x")).thenReturn(
            Single.error(
                failure
            )
        )
        repositoryViewModel.loadRepositoryReadMe("kotlin", "x")
        repositoryViewModel.repositoryReadMe.observeForever(repositoryReadMeObserver)

        val capture = argumentCaptor<String>()

        verify(progressObserver).onChanged(true)
        verify(repositoryReadMeObserver).onChanged(null)
        verify(errorObserver).onChanged(capture.capture())
        verify(progressObserver).onChanged(false)
    }
}