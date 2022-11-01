package com.example.githubapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.HomeScreenState
import com.example.githubapp.paging.DefaultPaginator
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val gitHubRepository: GitHubRepository
) : ViewModel() {
    var homeScreenState by mutableStateOf(HomeScreenState())

    private val paginator = DefaultPaginator(
        initialKey = homeScreenState.page,
        onLoadUpdated = {
            homeScreenState = homeScreenState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            gitHubRepository.getSearchedRepositories(
                searchQuery = "",
                page = nextPage,
                pageSize = PAGE_SIZE
            )
        },
        getNextKey = {
            homeScreenState.page + 1
        },
        onError = {
            homeScreenState = homeScreenState.copy(error = it)
        },
        onSuccess = { items, newKey ->
            homeScreenState = homeScreenState.copy(
                items = homeScreenState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun searchRepositories(searchQuery: String) {
        viewModelScope.launch {
            homeScreenState = homeScreenState.copy(
                isLoading = false,
                items = emptyList(),
                error = null,
                endReached = false,
                page = 1
            )
            paginator.reset()
            paginator.onRequest = { nextPage ->
                gitHubRepository.getSearchedRepositories(
                    searchQuery = searchQuery,
                    page = nextPage,
                    pageSize = PAGE_SIZE
                )
            }
            paginator.loadNextItems()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
