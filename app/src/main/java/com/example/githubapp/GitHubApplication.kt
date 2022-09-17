package com.example.githubapp

import android.app.Application
import com.example.githubapp.di.modules.dataModule
import com.example.githubapp.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitHubApplication)
            modules(
                viewModelModule,
                dataModule
            )
        }
    }
}
