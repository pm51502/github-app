package com.example.githubapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubapp.R
import com.example.githubapp.network.Resource
import com.example.githubapp.ui.navigation.AppScreen
import com.example.githubapp.ui.navigation.navigateToScreen
import com.example.githubapp.ui.shared.components.*
import com.example.githubapp.viewmodels.HomeViewModel
import org.koin.androidx.compose.viewModel
import java.time.ZonedDateTime

@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel by viewModel<HomeViewModel>()
    val searchedReposResource = homeViewModel.searchedReposStateFlow.collectAsState().value

    var showSearchBar by remember { mutableStateOf(true) }
    var showSortFieldPicker by remember { mutableStateOf(true) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedSortField: SortField? by rememberSaveable { mutableStateOf(null) }
    val uriHandler = LocalUriHandler.current

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.home_screen_vertical_padding)))
        ScreenDashboard(
            screenTitle = stringResource(id = R.string.home),
            onSearchIconClick = { showSearchBar = showSearchBar.not() },
            onFilterIconClick = { showSortFieldPicker = showSortFieldPicker.not() },
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.home_screen_horizontal_padding)
            )
        )

        if (showSearchBar) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearchDone = {
                    homeViewModel.searchRepositories(searchQuery = searchQuery)
                },
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.home_screen_horizontal_padding)
                )
            )
        }

        val filterList = SortField.values().toList()
        if (showSortFieldPicker) {
            SortFieldPicker(
                options = filterList,
                onOptionSelect = {
                    selectedSortField = if (selectedSortField == it) null else it
                },
                selectedOption = selectedSortField,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.filter_picker_height))
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.home_screen_horizontal_padding)
                    )
            )
        }

        when (searchedReposResource) {
            is Resource.Success -> {
                val repositoryList = searchedReposResource.data?.repositoryList
                val filteredRepositoryList = when (selectedSortField) {
                    SortField.STARS -> repositoryList?.sortedByDescending { it.starsCount }
                    SortField.FORKS -> repositoryList?.sortedByDescending { it.forksCount }
                    SortField.UPDATED -> repositoryList?.sortedByDescending { ZonedDateTime.parse(it.updatedAt) }
                    null -> repositoryList
                }

                if (filteredRepositoryList != null) {
                    RepositoryList(
                        repositoryList = filteredRepositoryList,
                        onItemClick = { repoOwner, repoName ->
                            navigateToScreen(
                                navController = navController,
                                route = "${AppScreen.Details.route}/$repoOwner/$repoName"
                            )
                        },
                        onAvatarClick = { htmlUrl ->
                            uriHandler.openUri(htmlUrl)
                        }
                    )
                }
            }
            is Resource.Error -> {
                val message = searchedReposResource.message
                if (message != null) {
                    ErrorMessageText(message = message)
                }
            }
            is Resource.Loading -> {
                ProgressIndicator()
            }
        }
    }
}
