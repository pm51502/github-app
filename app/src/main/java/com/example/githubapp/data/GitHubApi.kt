package com.example.githubapp.data

import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.network.RepositoryDetailsResponse
import com.example.githubapp.utils.toRepositoryDetails
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface GitHubApi {
    suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>>
    suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails>
    suspend fun getUserRepositories(repoOwner: String, page: Int, pageSize: Int): Result<List<Repository>>
}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>> {
        return try {
            val repositoriesResponse =
                httpClient.get<RepositoriesResponse>(urlString = "${ApiConstants.SEARCH_URL}?q=$searchQuery&page=$page&per_page=$pageSize")
            Result.success(repositoriesResponse.repositoryList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> {
        return try {
            val repositoryDetailsResponse =
                httpClient.get<RepositoryDetailsResponse>(urlString = "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
            Result.success(repositoryDetailsResponse.toRepositoryDetails())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getUserRepositories(repoOwner: String, page: Int, pageSize: Int): Result<List<Repository>> {
        return try {
            val response: HttpResponse = httpClient.get(urlString = "${ApiConstants.USER_REPOS_URL}/$repoOwner/repos?page=$page&per_page=$pageSize")
            val responseStr: String = response.receive()
            val userRepositories = json.decodeFromString<List<Repository>>(responseStr)

            Result.success(userRepositories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

object ApiConstants {
    private const val BASE_URL = "https://api.github.com"
    const val SEARCH_URL = "$BASE_URL/search/repositories"
    const val DETAILS_URL = "$BASE_URL/repos"
    const val USER_REPOS_URL = "$BASE_URL/users"
}
