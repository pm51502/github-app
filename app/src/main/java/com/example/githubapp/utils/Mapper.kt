package com.example.githubapp.utils

import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.network.RepositoryDetailsResponse

fun RepositoryDetailsResponse.toRepositoryDetails() = RepositoryDetails(
    id = id,
    name = name,
    description = description,
    owner = owner,
    watchersCount = watchersCount,
    forksCount = forksCount,
    issuesCount = issuesCount,
    starsCount = starsCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    htmlUrl = htmlUrl
)
