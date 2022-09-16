package com.example.githubapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.githubapp.R
import com.example.githubapp.ui.shared.components.RepositoryDetailsCard
import com.example.githubapp.ui.shared.components.RepositoryOwnerCard
import com.example.githubapp.ui.shared.components.ScreenSectionTitle

@Composable
fun DetailsScreen(
    navController: NavController,
    repoOwner: String?,
    repoName: String?,
) {
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

val repositoryDetails = RepositoryDetails(
    id = 1,
    name = "android-vjestina-tmdb",
    description = "Movie app made during Five Android bootcamp",
    owner = RepositoryOwner(
        name = "pm51502",
        avatarId = R.drawable.ic_github,
        htmlUrl = "https://github.com/pm51502"
    ),
    watchersCount = 5,
    forksCount = 5,
    issuesCount = 0,
    starsCount = 5,
    createdAt = "2021-09-15T09:53:18Z",
    updatedAt = "2022-09-15T09:53:18Z",
    htmlUrl = "https://github.com/pm51502/android-vjestina-tmdb"
)

data class RepositoryDetails(
    val id: Int,
    val name: String,
    val description: String,
    val owner: RepositoryOwner,
    val watchersCount: Int,
    val forksCount: Int,
    val issuesCount: Int,
    val starsCount: Int, //stargazers_count
    val createdAt: String,
    val updatedAt: String,
    val htmlUrl: String,
)
