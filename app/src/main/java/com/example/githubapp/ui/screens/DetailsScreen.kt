package com.example.githubapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.githubapp.R
import com.example.githubapp.data.RepoData
import com.example.githubapp.network.Resource
import com.example.githubapp.ui.shared.components.*
import com.example.githubapp.utils.toRepositoryDetails
import com.example.githubapp.viewmodels.DetailsViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    repoOwner: String?,
    repoName: String?,
) {
    val detailsViewModel by viewModel<DetailsViewModel> {
        parametersOf(
            RepoData(
                repoOwner = repoOwner,
                repoName = repoName
            )
        )
    }
    val repositoryDetailsResource = detailsViewModel.repoDetailsStateFlow.collectAsState().value

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

        when (repositoryDetailsResource) {
            is Resource.Success -> {
                val repositoryDetails = repositoryDetailsResource.data?.toRepositoryDetails()

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
                }
            }
            is Resource.Error -> {
                val message = repositoryDetailsResource.message
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
