package com.example.githubapp.di.modules

import com.example.githubapp.viewmodels.DetailsViewModel
import com.example.githubapp.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            gitHubRepository = get()
        )
    }
    viewModel { params ->
        DetailsViewModel(
            gitHubRepository = get(),
            repoData = params.get()
        )
    }
}
