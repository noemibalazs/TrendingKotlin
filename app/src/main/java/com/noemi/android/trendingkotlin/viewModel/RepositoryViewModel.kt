package com.noemi.android.trendingkotlin.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noemi.android.trendingkotlin.api.localDataSource.GitHubLocalService
import com.noemi.android.trendingkotlin.api.model.*
import com.noemi.android.trendingkotlin.api.remoteDataSource.GitHubRemoteService
import com.noemi.android.trendingkotlin.mapper.Mapper
import com.noemi.android.trendingkotlin.room.RepositoryEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.*
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(
    private val gitHubRemoteService: GitHubRemoteService,
    private val gitHubLocalService: GitHubLocalService
) : BaseViewModel() {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val trendingRepositories = MutableLiveData<MutableList<Repository>>()
    val searchClickEvent = MutableLiveData<Boolean>()
    val filteredRepositories = MutableLiveData<MutableList<Repository>>()
    val filterError = MutableLiveData<Boolean>()
    val clearClickEvent = MutableLiveData<Boolean>()
    val repositoryDetails = MutableLiveData<RepositoryDetails>()
    val repositoryReadMe = MutableLiveData<RepositoryReadMe>()

    fun loadTrendingRepositories(mapper: Mapper) {
        compositeDisposable.clear()
        val disposable = gitHubRemoteService.getTrendingRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<KotlinRepositories>() {
                override fun onSuccess(result: KotlinRepositories) {
                    trendingRepositories.value = result.items
                    insertRepositories(mapper, result.items)
                    Log.d(
                        TAG,
                        "loadTrendingRepositories() - onSuccess() - count: ${result.total_count}"
                    )
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "loadTrendingRepositories() - onError()")
                }
            })

        compositeDisposable.add(disposable)
    }

    private fun insertRepositories(mapper: Mapper, repositoryList: MutableList<Repository>) {
        Log.d(TAG, "insertRepositories()")
        val entities = mutableListOf<RepositoryEntity>()
        repositoryList.forEach { entities.add(mapper.mapRepositoryToEntity(it)) }
        CoroutineScope(Dispatchers.IO).launch {
            gitHubLocalService.insertRepositoryList(entities)
        }
    }

    fun onSearchClicked() {
        Log.d(TAG, "onSearchClicked()")
        searchClickEvent.value = true
    }

    fun filterRepositories(stars: Int, mapper: Mapper) {
        progressEvent.value = true
        val repositories = mutableListOf<Repository>()
        CoroutineScope(Dispatchers.IO).launch {
            val filteredResult = gitHubLocalService.getFilteredRepositoryList(stars)
            withContext(Dispatchers.Main) {
                progressEvent.value = false
                try {
                    filteredResult.forEach {
                        repositories.add(
                            mapper.mapRepositoryEntityToRepository(
                                it
                            )
                        )
                    }
                    filteredRepositories.value = repositories
                    filterError.value = repositories.isEmpty()
                    Log.d(TAG, "filterRepositories()")
                } catch (e: Exception) {
                    errorEvent.value = e.message ?: ERROR_MESSAGE
                    Log.e(TAG, "filterRepositories() - error: ${e.printStackTrace()}")
                }
            }
        }
    }

    fun onClearClicked() {
        Log.d(TAG, "onClearClicked()")
        clearClickEvent.value = true
    }

    fun loadAllRepositoryDetails(id: Int, repositoryFullName: String) {
        compositeDisposable.clear()
        val fullName = repositoryFullName.split("/")
        val disposable1 = loadRepositoryDetails(id)
        val disposable2 = loadRepositoryReadMe(fullName[0], fullName[1])
        compositeDisposable.addAll(disposable1, disposable2)
    }

    fun loadRepositoryDetails(id: Int): Disposable {
        Log.d(TAG, "loadRepositoryDetails() - id: $id")
        return gitHubRemoteService.getRepositoryDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<RepositoryDetails>() {

                override fun onSuccess(result: RepositoryDetails) {
                    repositoryDetails.value = result
                    Log.d(TAG, "loadRepositoryDetails() - onSuccess()")
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "loadRepositoryDetails() - onError()")
                }
            })
    }

    fun loadRepositoryReadMe(name: String, repo: String): Disposable {
        Log.d(TAG, "loadRepositoryReadMe()")
        return gitHubRemoteService.getRepositoryReadMe(name, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressEvent.value = true
            }
            .doFinally {
                progressEvent.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<RepositoryReadMe>() {
                override fun onSuccess(readMe: RepositoryReadMe) {
                    repositoryReadMe.value = readMe
                    Log.d(TAG, "loadRepositoryReadMe() - onSuccess()")
                }

                override fun onError(throwable: Throwable) {
                    errorEvent.value = throwable.message ?: ERROR_MESSAGE
                    Log.e(TAG, "loadRepositoryReadMe() - onError()")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val gitHubRemoteService: GitHubRemoteService,
        private val gitHubLocalService: GitHubLocalService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoryViewModel(gitHubRemoteService, gitHubLocalService) as T
        }
    }

    companion object {

        private const val TAG = "RepositoryViewModel"
        private const val ERROR_MESSAGE = "An error has occurred!"
    }
}