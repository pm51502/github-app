package com.example.githubapp.data

import com.example.githubapp.network.Repository

data class HomeScreenState(
    val isLoading: Boolean = false,
    val items: List<Repository> = emptyList(),
    val error: Throwable? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)
