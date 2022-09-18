package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Adjust
import androidx.compose.material.icons.sharp.CallSplit
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material.icons.sharp.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.githubapp.R
import com.example.githubapp.network.Repository
import com.example.githubapp.utils.formatTime

@Composable
fun RepositoryListItem(
    modifier: Modifier = Modifier,
    repository: Repository,
    onItemClick: (String, String) -> Unit,
    onAvatarClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.repository_list_item_border_radius)),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = dimensionResource(id = R.dimen.repository_list_item_elevation),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.repository_list_item_padding))
            .fillMaxWidth()
            .clickable { onItemClick.invoke(repository.owner.name, repository.name) },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_list_item_horizontal_spacing)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = repository.owner.avatarUrl),
                contentDescription = stringResource(id = R.string.repository_owner_avatar),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.repository_list_item_avatar_padding))
                    .size(dimensionResource(id = R.dimen.repository_list_item_avatar_size))
                    .clickable { onAvatarClick.invoke(repository.owner.htmlUrl) }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_list_item_vertical_spacing)),
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.repository_list_item_vertrical_padding))
            ) {
                Text(
                    text = repository.name,
                    fontWeight = FontWeight.Bold
                )
                Text(text = repository.owner.name)

                RepositoryStatsRow(stats = repository.getStats())

                Text(text = "Updated: ${repository.updatedAt.formatTime()}")
            }
        }
    }
}

data class RepositoryStat(
    val imageVector: ImageVector,
    val count: Int
)

fun Repository.getStats() : List<RepositoryStat> = listOf(
    RepositoryStat(
        imageVector = Icons.Sharp.Visibility,
        count = this.watchersCount
    ),
    RepositoryStat(
        imageVector = Icons.Sharp.CallSplit,
        count = this.forksCount
    ),
    RepositoryStat(
        imageVector = Icons.Sharp.Adjust,
        count = this.issuesCount
    ),
    RepositoryStat(
        imageVector = Icons.Sharp.Star,
        count = this.starsCount
    )
)
