package com.example.githubapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.DetailsScreenState
import com.example.githubapp.data.RepoData
import com.example.githubapp.data.HomeScreenState
import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.network.RepositoryDetailsResponse
import com.example.githubapp.network.Resource
import com.example.githubapp.paging.DefaultPaginator
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val gitHubRepository: GitHubRepository,
    repoData: RepoData
) : ViewModel() {
    var detailsScreenState by mutableStateOf(DetailsScreenState())

//    private val _repoDetailsStateFlow = MutableStateFlow<Resource<RepositoryDetailsResponse>>(
//        Resource.Success(data = RepositoryDetailsResponse())
//    )
//    val repoDetailsStateFlow = _repoDetailsStateFlow.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = detailsScreenState.page,
        onLoadUpdated = {
            detailsScreenState = detailsScreenState.copy(isLoadingItems = it)
        },
        onRequest = { nextPage ->
            gitHubRepository.getUserRepositories(repoOwner = repoData.repoOwner)
//            gitHubRepository.getSearchedRepositories(
//                searchQuery = "",
//                page = nextPage,
//                pageSize = HomeViewModel.PAGE_SIZE
//            )
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

//            _repoDetailsStateFlow.value = Resource.Loading()
//
//            _repoDetailsStateFlow.value =
//                gitHubRepository.getRepositoryDetails(repoData = repoData).first()
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}
