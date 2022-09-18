package com.example.githubapp.data

import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.RepositoryDetailsResponse
import io.ktor.client.*
import io.ktor.client.request.*

interface GitHubApi {
    suspend fun getSearchedRepositories(searchQuery: String): RepositoriesResponse
    suspend fun getRepositoryDetails(repoData: RepoData): RepositoryDetailsResponse
}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun getSearchedRepositories(searchQuery: String): RepositoriesResponse =
        try {
            httpClient.get(urlString = "${ApiConstants.SEARCH_URL}?q=$searchQuery")
        } catch (e: Exception) {
            println(e.message)
            RepositoriesResponse(repositoryList = emptyList())
        }

    override suspend fun getRepositoryDetails(repoData: RepoData): RepositoryDetailsResponse =
        try {
            httpClient.get(urlString = "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
        } catch (e: Exception) {
            println(e.message)
            RepositoryDetailsResponse()
        }
}

object ApiConstants {
    private const val BASE_URL = "https://api.github.com"
    const val SEARCH_URL = "$BASE_URL/search/repositories"
    const val DETAILS_URL = "$BASE_URL/repos"
}
