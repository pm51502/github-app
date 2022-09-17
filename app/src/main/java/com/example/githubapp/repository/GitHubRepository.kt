package com.example.githubapp.repository

import com.example.githubapp.data.GitHubApi
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.utils.toRepositoryDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

interface GitHubRepository {
    fun getSearchedRepositories(searchQuery: String): Flow<List<Repository>>
    fun getRepositoryDetails(repoData: RepoData): Flow<RepositoryDetails>
}

class GitHubRepositoryImpl(
    private val gitHubApi: GitHubApi
) : GitHubRepository {
    private val sharingScope = CoroutineScope(Dispatchers.Default)

    override fun getSearchedRepositories(searchQuery: String): Flow<List<Repository>> = flow {
        emit(gitHubApi.getSearchedRepositories(searchQuery = searchQuery).repositoryList)
    }.shareIn(
        sharingScope,
        SharingStarted.Lazily,
        replay = 1
    )

    override fun getRepositoryDetails(repoData: RepoData): Flow<RepositoryDetails> = flow {
        emit(gitHubApi.getRepositoryDetails(repoData = repoData).toRepositoryDetails())
    }.shareIn(
        sharingScope,
        SharingStarted.Eagerly,
        replay = 1
    )
}
