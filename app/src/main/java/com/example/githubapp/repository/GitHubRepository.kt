package com.example.githubapp.repository

import com.example.githubapp.data.GitHubApi
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.*
import com.example.githubapp.utils.toRepositoryDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

interface GitHubRepository {
    fun getSearchedRepositories(searchQuery: String): Flow<Resource<RepositoriesResponse>>
    fun getRepositoryDetails(repoData: RepoData): Flow<Resource<RepositoryDetailsResponse>>
}

class GitHubRepositoryImpl(
    private val gitHubApi: GitHubApi
) : GitHubRepository {
    private val sharingScope = CoroutineScope(Dispatchers.Default)

    override fun getSearchedRepositories(searchQuery: String): Flow<Resource<RepositoriesResponse>> = flow {
        emit(gitHubApi.getSearchedRepositories(searchQuery = searchQuery))
    }.shareIn(
        sharingScope,
        SharingStarted.Lazily,
        replay = 1
    )

    override fun getRepositoryDetails(repoData: RepoData): Flow<Resource<RepositoryDetailsResponse>> = flow {
        emit(gitHubApi.getRepositoryDetails(repoData = repoData))
    }.shareIn(
        sharingScope,
        SharingStarted.Eagerly,
        replay = 1
    )
}
