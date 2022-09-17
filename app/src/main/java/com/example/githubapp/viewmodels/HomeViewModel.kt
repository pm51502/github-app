package com.example.githubapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.network.Repository
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    val gitHubRepository: GitHubRepository
) : ViewModel() {
    private val _searchedReposStateFlow = MutableStateFlow(
        emptyList<Repository>()
    )
    val searchedReposStateFlow = _searchedReposStateFlow.asStateFlow()

    fun searchRepositories(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedReposStateFlow.value =
                gitHubRepository.getSearchedRepositories(searchQuery = searchQuery).first()
        }
    }
}
