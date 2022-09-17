package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.OpenInBrowser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.githubapp.R
import com.example.githubapp.network.RepositoryOwner

@Composable
fun RepositoryOwnerCard(
    modifier: Modifier = Modifier,
    repositoryOwner: RepositoryOwner,
    onOpenInBrowserClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.repository_owner_card_border_radius)),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = dimensionResource(id = R.dimen.repository_owner_card_elevation),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_owner_card_vertical_spacing)),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.repository_owner_card_vertical_padding))
        ) {
            if (repositoryOwner.avatarUrl != null && repositoryOwner.avatarUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = repositoryOwner.avatarUrl),
                    //painterResource(id = repositoryOwner.avatarId),
                    contentDescription = stringResource(id = R.string.repository_owner_avatar),
                    modifier = Modifier.size(85.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = repositoryOwner.name,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { onOpenInBrowserClick.invoke(repositoryOwner.htmlUrl) }) {
                    Icon(
                        imageVector = Icons.Sharp.OpenInBrowser,
                        contentDescription = stringResource(id = R.string.open_in_browser_icon)
                    )
                }
            }
        }
    }
}
