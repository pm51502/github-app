package com.example.githubapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubapp.R
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
    val repositoryList = homeViewModel.searchedReposStateFlow.collectAsState().value

    var showSearchBar by remember { mutableStateOf(true) }
    var showFilterPicker by remember { mutableStateOf(true) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedSortField: SortField? by rememberSaveable { mutableStateOf(null) }
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.home_screen_padding)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenDashboard(
            screenTitle = stringResource(id = R.string.home),
            onSearchIconClick = { showSearchBar = showSearchBar.not() },
            onFilterIconClick = { showFilterPicker = showFilterPicker.not() }
        )

        if (showSearchBar) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearchDone = {
                    homeViewModel.searchRepositories(searchQuery = searchQuery)
                }
            )
        }

        val filterList = SortField.values().toList()
        if (showFilterPicker) {
            SortFieldPicker(
                options = filterList,
                onOptionSelect = {
                    selectedSortField = if (selectedSortField == it) null else it
                },
                selectedOption = selectedSortField,
                modifier = Modifier.height(dimensionResource(id = R.dimen.filter_picker_height))
            )
        }

        val filteredRepositoryList = when (selectedSortField) {
            SortField.STARS -> repositoryList.sortedByDescending { it.starsCount }
            SortField.FORKS -> repositoryList.sortedByDescending { it.forksCount }
            SortField.UPDATED -> repositoryList.sortedByDescending { ZonedDateTime.parse(it.updatedAt) }
            null -> repositoryList
        }

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
