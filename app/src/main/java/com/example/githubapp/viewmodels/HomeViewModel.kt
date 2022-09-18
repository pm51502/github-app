package com.example.githubapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.network.RepositoriesResponse
import com.example.githubapp.network.Repository
import com.example.githubapp.network.Resource
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    val gitHubRepository: GitHubRepository
) : ViewModel() {
    private val _searchedReposStateFlow = MutableStateFlow<Resource<RepositoriesResponse>>(
        Resource.Success(data = RepositoriesResponse(repositoryList = emptyList()))
    )
    val searchedReposStateFlow = _searchedReposStateFlow.asStateFlow()

    fun searchRepositories(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedReposStateFlow.value = Resource.Loading()

            _searchedReposStateFlow.value =
                gitHubRepository.getSearchedRepositories(searchQuery = searchQuery).first()
        }
    }
}
