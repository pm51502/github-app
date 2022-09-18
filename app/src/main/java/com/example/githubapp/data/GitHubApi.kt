package com.example.githubapp.data

import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.RepositoryDetailsResponse
import com.example.githubapp.network.Resource
import io.ktor.client.*
import io.ktor.client.request.*

interface GitHubApi {
    suspend fun getSearchedRepositories(searchQuery: String): Resource<RepositoriesResponse>
    suspend fun getRepositoryDetails(repoData: RepoData): Resource<RepositoryDetailsResponse>
}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun getSearchedRepositories(searchQuery: String): Resource<RepositoriesResponse> {
        return try {
            val repositoriesResponse =
                httpClient.get<RepositoriesResponse>(urlString = "${ApiConstants.SEARCH_URL}?q=$searchQuery")
            Resource.Success(data = repositoriesResponse)
        } catch (e: Exception) {
            Resource.Error(errorMessage = e.message.toString())
        }
    }

    override suspend fun getRepositoryDetails(repoData: RepoData): Resource<RepositoryDetailsResponse> {
        return try {
            val repositoryDetailsResponse =
                httpClient.get<RepositoryDetailsResponse>(urlString = "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
            Resource.Success(data = repositoryDetailsResponse)
        } catch (e: Exception) {
            Resource.Error(errorMessage = e.message.toString())
        }
    }
}

object ApiConstants {
    private const val BASE_URL = "https://api.github.com"
    const val SEARCH_URL = "$BASE_URL/search/repositories"
    const val DETAILS_URL = "$BASE_URL/repos"
}
