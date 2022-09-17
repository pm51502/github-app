package com.example.githubapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesResponse(
    @SerialName("items")
    val repositoryList: List<Repository>
)

@Serializable
data class Repository(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("owner")
    val owner: RepositoryOwner, //
    @SerialName("watchers_count")
    val watchersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("open_issues")
    val issuesCount: Int,
    @SerialName("stargazers_count")
    val starsCount: Int,
    @SerialName("updated_at")
    val updatedAt: String,
)

@Serializable
data class RepositoryOwner(
    @SerialName("login")
    val name: String = "",
    @SerialName("avatar_url")
    val avatarUrl: String? = "",
    @SerialName("html_url")
    val htmlUrl: String = "",
    //val avatarId: Int = R.drawable.ic_github,
)

@Serializable
data class RepositoryDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("owner")
    val owner: RepositoryOwner,
    @SerialName("watchers_count")
    val watchersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("open_issues")
    val issuesCount: Int,
    @SerialName("stargazers_count")
    val starsCount: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("html_url")
    val htmlUrl: String,
)

data class RepositoryDetails(
    val id: Int = 0,
    val name: String = "",
    val description: String? = "",
    val owner: RepositoryOwner = RepositoryOwner(),
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val issuesCount: Int = 0,
    val starsCount: Int = 0, //stargazers_count
    val createdAt: String = "2011-09-15T09:53:18Z",
    val updatedAt: String = "2011-09-15T09:53:18Z",
    val htmlUrl: String = "",
)
