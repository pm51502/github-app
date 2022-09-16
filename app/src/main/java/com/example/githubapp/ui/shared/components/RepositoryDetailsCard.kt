package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.githubapp.R
import com.example.githubapp.ui.screens.RepositoryDetails
import com.example.githubapp.utils.formatTime

@Composable
fun RepositoryDetailsCard(
    modifier: Modifier = Modifier,
    repositoryDetails: RepositoryDetails,
    onOpenInBrowserClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.repository_details_card_border_radius)),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = dimensionResource(id = R.dimen.repository_details_card_elevation),
        modifier = modifier.fillMaxWidth()
    ) {
        val repositoryStats = listOf(
            RepositoryStat(
                imageVector = Icons.Sharp.Visibility,
                count = repositoryDetails.watchersCount
            ),
            RepositoryStat(
                imageVector = Icons.Sharp.CallSplit,
                count = repositoryDetails.forksCount
            ),
            RepositoryStat(
                imageVector = Icons.Sharp.Adjust,
                count = repositoryDetails.issuesCount
            ),
            RepositoryStat(
                imageVector = Icons.Sharp.Star,
                count = repositoryDetails.starsCount
            ),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_details_card_spacing)),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.repository_details_card_vertical_padding))
        ) {
            Text(
                text = repositoryDetails.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h1
            )
            Text(
                text = repositoryDetails.description,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.repository_details_card_desc_padding))
            )

            RepositoryStatsRow(stats = repositoryStats)

            Column {
                Text(text = "Created: ${repositoryDetails.createdAt.formatTime()}")
                Text(text = "Updated: ${repositoryDetails.updatedAt.formatTime()}")
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(onClick = { onOpenInBrowserClick.invoke(repositoryDetails.htmlUrl) }) {
                    Icon(
                        imageVector = Icons.Sharp.OpenInBrowser,
                        contentDescription = stringResource(id = R.string.open_in_browser_icon)
                    )
                }

                IconButton(onClick = { onShareClick.invoke(repositoryDetails.htmlUrl) }) {
                    Icon(
                        imageVector = Icons.Sharp.Share,
                        contentDescription = stringResource(id = R.string.share_icon)
                    )
                }
            }
        }
    }
}
