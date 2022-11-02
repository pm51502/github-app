package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray

@Composable
fun UserReposDashboard(
    modifier: Modifier = Modifier,
    repoOwner: String?,
    onExpandClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScreenSectionTitle(title = "More from $repoOwner")

        IconButton(onClick = { onExpandClick.invoke() }) {
            Icon(
                imageVector = Icons.Sharp.ExpandMore,
                contentDescription = stringResource(id = R.string.expand_icon),
                tint = GithubGray
            )
        }
    }
}
