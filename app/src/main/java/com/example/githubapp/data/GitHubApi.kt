package com.example.githubapp.data

import android.util.Log
import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.network.RepositoryDetailsResponse
import com.example.githubapp.utils.toRepositoryDetails
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface GitHubApi {
    suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>>
    suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> //Resource<RepositoryDetailsResponse>
    suspend fun getUserRepositories(repoOwner: String): Result<List<Repository>>
}

class GitHubApiImpl(
    private val httpClient: HttpClient
) : GitHubApi {
    override suspend fun getSearchedRepositories(searchQuery: String, page: Int, pageSize: Int): Result<List<Repository>> {
        return try {
            val repositoriesResponse =
                httpClient.get<RepositoriesResponse>(urlString = "${ApiConstants.SEARCH_URL}?q=$searchQuery&page=$page&per_page=$pageSize")  //&page=1&per_page=10
            Log.i("---API CALL SUCCESS---", "${ApiConstants.SEARCH_URL}?q=$searchQuery&page=$page&per_page=$pageSize")
            Result.success(repositoriesResponse.repositoryList)
        } catch (e: Exception) {
            Log.i("---API CALL ERROR---", "${ApiConstants.SEARCH_URL}?q=$searchQuery&page=$page&per_page=$pageSize\n$e")
            Result.failure(e)
        }
    }

    override suspend fun getRepositoryDetails(repoData: RepoData): Result<RepositoryDetails> {
        return try {
            val repositoryDetailsResponse =
                httpClient.get<RepositoryDetailsResponse>(urlString = "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
            Log.i("---API CALL SUCCESS---", "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
            Result.success(repositoryDetailsResponse.toRepositoryDetails())
            //Resource.Success(data = repositoryDetailsResponse)
        } catch (e: Exception) {
            Log.i("---API CALL ERROR---", "${ApiConstants.DETAILS_URL}/${repoData.repoOwner}/${repoData.repoName}")
            Result.failure(e)
            //Resource.Error(errorMessage = e.message.toString())
        }
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getUserRepositories(repoOwner: String): Result<List<Repository>> {
        return try {
            val response: HttpResponse = httpClient.get(urlString = "${ApiConstants.USER_REPOS_URL}/$repoOwner/repos")
            val responseStr: String = response.receive()
            Log.i("---RAW HTTP RESPONSE---", responseStr)

            val userRepositories = json.decodeFromString<List<Repository>>(responseStr)

//            val userRepositoriesResponse =
//                httpClient.get<UserRepositoriesResponse>(urlString = "${ApiConstants.USER_REPOS_URL}/$repoOwner/repos")
            Log.i("---API CALL SUCCESS---", "${ApiConstants.USER_REPOS_URL}/$repoOwner/repos")
            Result.success(userRepositories)
        } catch (e: Exception) {
            Log.i("---API CALL ERROR---", "${ApiConstants.USER_REPOS_URL}/$repoOwner/repos\n$e")

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
