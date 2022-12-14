package com.example.githubapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.githubapp.R
import com.example.githubapp.data.RepoData
import com.example.githubapp.ui.navigation.AppScreen
import com.example.githubapp.ui.navigation.navigateToScreen
import com.example.githubapp.ui.shared.components.*
import com.example.githubapp.ui.theme.GithubGray
import com.example.githubapp.viewmodels.DetailsViewModel
import io.ktor.client.features.*
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    navController: NavController,
    repoOwner: String?,
    repoName: String?,
) {
    val repoData = RepoData(repoOwner = repoOwner, repoName = repoName)
    val detailsViewModel by viewModel<DetailsViewModel> { parametersOf(repoData) }

    var showUserRepos by remember { mutableStateOf(true) }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    val openInBrowserClick = { uri: String ->
        uriHandler.openUri(uri)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.details_screen_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenSectionTitle(
            title = stringResource(id = R.string.owner_details),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacing)))

        val detailsError = detailsViewModel.detailsScreenState.detailsError
        if (detailsError != null && detailsError !is ClientRequestException) {
            RetryButton(
                onClick = {
                    detailsViewModel.detailsScreenState =
                        detailsViewModel.detailsScreenState.copy(detailsError = null)
                    detailsViewModel.getRepoDetails(repoData = repoData)
                }
            )
        } else {
            val repositoryDetails = detailsViewModel.detailsScreenState.repoDetails

            if (repositoryDetails != null) {
                RepositoryOwnerCard(
                    repositoryOwner = repositoryDetails.owner,
                    onOpenInBrowserClick = openInBrowserClick
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_spacing)))

                ScreenSectionTitle(
                    title = stringResource(id = R.string.repository_details),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacing)))

                RepositoryDetailsCard(
                    repositoryDetails = repositoryDetails,
                    onOpenInBrowserClick = openInBrowserClick,
                    onShareClick = { htmlUri ->
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, htmlUri)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, null))
                    }
                )
            } else if (detailsViewModel.detailsScreenState.isLoadingDetails) {
                ProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacing)))
        UserReposDashboard(
            repoOwner = repoOwner,
            onExpandClick = { showUserRepos = showUserRepos.not() }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacing)))

        if (showUserRepos) {
            val itemsError = detailsViewModel.detailsScreenState.itemsError
            if (itemsError != null && itemsError !is ClientRequestException) {
                RetryButton(
                    onClick = {
                        detailsViewModel.detailsScreenState =
                            detailsViewModel.detailsScreenState.copy(itemsError = null)
                        detailsViewModel.loadNextItems()
                    }
                )
            } else {
                val userRepositoryList =
                    detailsViewModel.detailsScreenState.items.filter { it.name != repoName }

                UserRepositoryList(
                    userRepositoryList = userRepositoryList,
                    onItemClick = { repoOwner, repoName ->
                        navController.popBackStack()
                        navigateToScreen(
                            navController = navController,
                            route = "${AppScreen.Details.route}/$repoOwner/$repoName"
                        )
                    },
                    loadNextItems = { detailsViewModel.loadNextItems() },
                    screenState = detailsViewModel.detailsScreenState
                )
            }
        }
    }
}
