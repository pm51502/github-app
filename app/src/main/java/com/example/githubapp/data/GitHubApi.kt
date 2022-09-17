package com.example.githubapp.data

import android.content.Context
import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.RepositoryDetailsResponse
import io.ktor.client.*
import io.ktor.client.request.*

interface GitHubApi {
    suspend fun getSearchedRepositories(searchQuery: String): RepositoriesResponse
    suspend fun getRepositoryDetails(repoData: RepoData): RepositoryDetailsResponse
}

class GitHubApiImpl(
    private val httpClient: HttpClient,
    context: Context
) : GitHubApi {
    override suspend fun getSearchedRepositories(searchQuery: String): RepositoriesResponse =
        httpClient.get(urlString = "${ApiConstants.SEARCH_URL}?q=$searchQuery")

    override suspend fun getRepositoryDetails(repoData: RepoData): RepositoryDetailsResponse =
        httpClient.get(urlString = "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
}

object ApiConstants {
    const val SEARCH_URL = "https://api.github.com/search/repositories"
    const val DETAILS_URL = "https://api.github.com/repos"
}
