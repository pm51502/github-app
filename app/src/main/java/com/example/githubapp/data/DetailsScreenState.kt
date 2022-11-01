package com.example.githubapp.data

import com.example.githubapp.network.Repository
import com.example.githubapp.network.RepositoryDetails

data class DetailsScreenState(
    val repoDetails: RepositoryDetails? = null,
    val isLoadingDetails: Boolean = false,
    val detailsError: Throwable? = null,
    val items: List<Repository> = emptyList(),
    val isLoadingItems: Boolean = false,
    val itemsError: Throwable? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)
