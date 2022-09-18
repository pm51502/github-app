package com.example.githubapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.RepositoryDetailsResponse
import com.example.githubapp.network.Resource
import com.example.githubapp.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val gitHubRepository: GitHubRepository,
    repoData: RepoData
) : ViewModel() {
    private val _repoDetailsStateFlow = MutableStateFlow<Resource<RepositoryDetailsResponse>>(
        Resource.Success(data = RepositoryDetailsResponse())
    )
    val repoDetailsStateFlow = _repoDetailsStateFlow.asStateFlow()

    init {
        getRepoDetails(repoData = repoData)
    }

    private fun getRepoDetails(repoData: RepoData) {
        viewModelScope.launch(Dispatchers.IO) {
            _repoDetailsStateFlow.value = Resource.Loading()

            _repoDetailsStateFlow.value =
                gitHubRepository.getRepositoryDetails(repoData = repoData).first()
        }
    }
}
