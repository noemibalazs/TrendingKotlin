package com.noemi.android.trendingkotlin.di

import com.noemi.android.trendingkotlin.app.App
import com.noemi.android.trendingkotlin.details.RepositoryDetailsActivity
import com.noemi.android.trendingkotlin.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RemoteRepositoryModule::class,
    LocalRepositoryModule::class, ViewModelModule::class, PreferencesModule::class])
interface AppComponent {

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)

    fun inject(repositoryDetailsActivity: RepositoryDetailsActivity)
}