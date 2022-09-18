package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray

@Composable
fun RepositoryStatsRow(
    modifier: Modifier = Modifier,
    stats: List<RepositoryStat>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_stats_spacing)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stats.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = it.imageVector,
                    contentDescription = stringResource(id = R.string.repository_stat_icon),
                    tint = GithubGray,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.repository_stat_icon_size))
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.repository_stats_inner_spacing)))

                Text(
                    text = it.count.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
