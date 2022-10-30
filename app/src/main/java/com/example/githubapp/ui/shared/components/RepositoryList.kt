package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.githubapp.R
import com.example.githubapp.network.Repository

@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositoryList: List<Repository>,
    onItemClick: (String, String) -> Unit,
    onAvatarClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.repository_list_horizontal_padding),
            vertical = dimensionResource(id = R.dimen.repository_list_vertical_padding)
        )
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
