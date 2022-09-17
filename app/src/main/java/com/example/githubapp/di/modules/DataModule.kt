package com.example.githubapp.di.modules

import com.example.githubapp.data.GitHubApi
import com.example.githubapp.data.GitHubApiImpl
import com.example.githubapp.network.httpClient
import com.example.githubapp.repository.GitHubRepository
import com.example.githubapp.repository.GitHubRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<GitHubRepository> {
        GitHubRepositoryImpl(
            gitHubApi = get()
        )
    }
    single<GitHubApi> {
        GitHubApiImpl(
            httpClient = get(),
            context = androidContext()
        )
    }
    single {
        httpClient
    }
}
