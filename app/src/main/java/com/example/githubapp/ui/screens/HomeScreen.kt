package com.example.githubapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
import java.time.ZonedDateTime

@Composable
fun HomeScreen(
    navController: NavController
) {
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
                onQueryChange = { searchQuery = it }
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

        selectedSortField?.let { Text(it.toString()) }
        Text(text = searchQuery)
    }
}

data class Repository(
    val id: Int,
    val name: String,
    val owner: RepositoryOwner,
    val watchersCount: Int,
    val forksCount: Int,
    val issuesCount: Int,
    val starsCount: Int, //stargazers_count
    val updatedAt: String,
)

data class RepositoryOwner(
    val name: String,
    //val avatarUrl: String,
    val avatarId: Int,
    val htmlUrl: String,
)

//val repositoryList = List(20) {
//    Repository(
//        id = 1,
//        name = "android-vjestina-tmdb",
//        owner = RepositoryOwner(
//            name = "pm51502",
//            avatarId = R.drawable.ic_github,
//            htmlUrl = "https://github.com/pm51502"
//        ),
//        watchersCount = 5,
//        forksCount = 5,
//        issuesCount = 0
//    )
//}

val repositoryList = listOf(
    Repository(
        id = 1,
        name = "android-vjestina-tmdb",
        owner = RepositoryOwner(
            name = "pm51502",
            avatarId = R.drawable.ic_github,
            htmlUrl = "https://github.com/pm51502"
        ),
        watchersCount = 1,
        forksCount = 2,
        issuesCount = 3,
        starsCount = 7,
        updatedAt = "2011-09-15T09:53:18Z"
    ),
    Repository(
        id = 1,
        name = "android-vjestina-tmdb",
        owner = RepositoryOwner(
            name = "pm51502",
            avatarId = R.drawable.ic_github,
            htmlUrl = "https://github.com/pm51502"
        ),
        watchersCount = 3,
        forksCount = 2,
        issuesCount = 6,
        starsCount = 2,
        updatedAt = "2021-09-15T09:53:18Z"
    ),
    Repository(
        id = 1,
        name = "android-vjestina-tmdb",
        owner = RepositoryOwner(
            name = "pm51502",
            avatarId = R.drawable.ic_github,
            htmlUrl = "https://github.com/pm51502"
        ),
        watchersCount = 16,
        forksCount = 8,
        issuesCount = 99,
        starsCount = 1,
        updatedAt = "2000-09-15T09:53:18Z"
    ),
    Repository(
        id = 1,
        name = "android-vjestina-tmdb",
        owner = RepositoryOwner(
            name = "pm51502",
            avatarId = R.drawable.ic_github,
            htmlUrl = "https://github.com/pm51502"
        ),
        watchersCount = 555,
        forksCount = 59,
        issuesCount = 0,
        starsCount = 0,
        updatedAt = "2019-09-15T09:53:18Z"
    ),
    Repository(
        id = 1,
        name = "android-vjestina-tmdb",
        owner = RepositoryOwner(
            name = "pm51502",
            avatarId = R.drawable.ic_github,
            htmlUrl = "https://github.com/pm51502"
        ),
        watchersCount = 7,
        forksCount = 2,
        issuesCount = 1,
        starsCount = 99,
        updatedAt = "2018-09-15T09:53:18Z"
    )
)
