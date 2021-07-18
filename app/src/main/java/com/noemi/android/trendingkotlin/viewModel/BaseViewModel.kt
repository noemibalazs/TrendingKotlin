package com.noemi.android.trendingkotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    abstract val compositeDisposable: CompositeDisposable
    val errorEvent = MutableLiveData<String>()
    val progressEvent = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}