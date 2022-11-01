package com.example.githubapp.repository

import com.example.githubapp.data.GitHubApi
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

interface GitHubRepository {
    suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>>
    suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> //Flow<Resource<RepositoryDetailsResponse>>
    suspend fun getUserRepositories(repoOwner: String?): Result<List<Repository>>
}

class GitHubRepositoryImpl(
    private val gitHubApi: GitHubApi
) : GitHubRepository {
    private val sharingScope = CoroutineScope(Dispatchers.Default)

    override suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>> =
        if (searchQuery.isEmpty()) Result.success(emptyList()) //Resource.Success(data = emptyList())
        else gitHubApi.getSearchedRepositories(searchQuery = searchQuery, page = page, pageSize = pageSize)

    override suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> =
        gitHubApi.getRepositoryDetails(repoData = repoData)

    override suspend fun getUserRepositories(repoOwner: String?): Result<List<Repository>> =
        if (repoOwner == null) Result.success(emptyList())
        else gitHubApi.getUserRepositories(repoOwner = repoOwner)

//    override fun getRepositoryDetails(repoData: RepoData): Flow<Resource<RepositoryDetailsResponse>> = flow {
//        emit(gitHubApi.getRepositoryDetails(repoData = repoData))
//    }.shareIn(
//        sharingScope,
//        SharingStarted.Eagerly,
//        replay = 1
//    )
}
