package com.example.githubapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.DetailsScreenState
import com.example.githubapp.data.RepoData
import com.example.githubapp.paging.DefaultPaginator
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val gitHubRepository: GitHubRepository,
    repoData: RepoData
) : ViewModel() {
    var detailsScreenState by mutableStateOf(DetailsScreenState())

    private val paginator = DefaultPaginator(
        initialKey = detailsScreenState.page,
        onLoadUpdated = {
            detailsScreenState = detailsScreenState.copy(isLoadingItems = it)
        },
        onRequest = { nextPage ->
            gitHubRepository.getUserRepositories(
                repoOwner = repoData.repoOwner,
                page = nextPage,
                pageSize = PAGE_SIZE
            )
        },
        getNextKey = {
            detailsScreenState.page + 1
        },
        onError = {
            detailsScreenState = detailsScreenState.copy(itemsError = it)
        },
        onSuccess = { items, newKey ->
            detailsScreenState = detailsScreenState.copy(
                items = detailsScreenState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        getRepoDetails(repoData = repoData)
        loadNextItems()
    }

    fun getRepoDetails(repoData: RepoData) {
        viewModelScope.launch {
            detailsScreenState = detailsScreenState.copy(isLoadingDetails = true)

            val result = gitHubRepository.getRepositoryDetails(repoData = repoData)
            result.fold(
                onSuccess = {
                    detailsScreenState = detailsScreenState.copy(
                        isLoadingDetails = false,
                        repoDetails = it
                    )
                },
                onFailure = {
                    detailsScreenState = detailsScreenState.copy(
                        isLoadingDetails = false,
                        detailsError = it
                    )
                }
            )
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
