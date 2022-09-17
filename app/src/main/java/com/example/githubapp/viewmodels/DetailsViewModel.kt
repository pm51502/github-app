package com.example.githubapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.RepositoryDetails
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailsViewModel(
    val gitHubRepository: GitHubRepository
) : ViewModel() {
    private val _repoDetailsStateFlow = MutableStateFlow(
        RepositoryDetails()
    )
    val repoDetailsStateFlow = _repoDetailsStateFlow.asStateFlow()

    fun getRepoDetails(repoData: RepoData) {
        viewModelScope.launch(Dispatchers.IO) {
            _repoDetailsStateFlow.value =
                gitHubRepository.getRepositoryDetails(repoData = repoData).first()
        }
    }
}
