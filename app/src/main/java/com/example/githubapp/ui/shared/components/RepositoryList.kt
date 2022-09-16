package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.githubapp.R
import com.example.githubapp.ui.screens.Repository

@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositoryList: List<Repository>,
    onItemClick: (String, String) -> Unit,
    onAvatarClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repository_list_spacing))
    ) {
        items(count = repositoryList.size) { index ->
            RepositoryListItem(
                repository = repositoryList[index],
                onItemClick = onItemClick,
                onAvatarClick = onAvatarClick
            )
        }
    }
}
