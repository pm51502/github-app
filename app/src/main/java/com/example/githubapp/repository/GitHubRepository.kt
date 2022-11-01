package com.example.githubapp.repository

import com.example.githubapp.data.GitHubApi
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails

interface GitHubRepository {
    suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>>
    suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails>
    suspend fun getUserRepositories(repoOwner: String?, page: Int, pageSize: Int): Result<List<Repository>>
}

class GitHubRepositoryImpl(
    private val gitHubApi: GitHubApi
) : GitHubRepository {
    override suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>> =
        if (searchQuery.isEmpty()) Result.success(emptyList())
        else gitHubApi.getSearchedRepositories(searchQuery = searchQuery, page = page, pageSize = pageSize)

    override suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> =
        gitHubApi.getRepositoryDetails(repoData = repoData)

    override suspend fun getUserRepositories(repoOwner: String?, page: Int, pageSize: Int): Result<List<Repository>> =
        if (repoOwner == null) Result.success(emptyList())
        else gitHubApi.getUserRepositories(repoOwner = repoOwner, page = page, pageSize = pageSize)
}
