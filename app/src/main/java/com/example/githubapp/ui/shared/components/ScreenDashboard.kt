package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray

@Composable
fun ScreenDashboard(
    modifier: Modifier = Modifier,
    screenTitle: String,
    onSearchIconClick: () -> Unit,
    onFilterIconClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScreenSectionTitle(title = screenTitle)

        Row {
            IconButton(onClick = { onSearchIconClick.invoke() }) {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = stringResource(id = R.string.search_icon),
                    tint = GithubGray
                )
            }

            IconButton(onClick = { onFilterIconClick.invoke() }) {
                Icon(
                    imageVector = Icons.Sharp.Tune,
                    contentDescription = stringResource(id = R.string.filter_icon),
                    tint = GithubGray
                )
            }
        }
    }
}
