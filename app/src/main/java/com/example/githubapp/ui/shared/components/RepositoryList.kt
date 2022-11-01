package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.githubapp.R
import com.example.githubapp.data.HomeScreenState
import com.example.githubapp.network.Repository

@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositoryList: List<Repository>,
    onItemClick: (String, String) -> Unit,
    onAvatarClick: (String) -> Unit,
    loadNextItems: () -> Unit,
    screenState: HomeScreenState,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.repository_list_horizontal_padding),
            vertical = dimensionResource(id = R.dimen.repository_list_vertical_padding)
        )
    ) {
        items(repositoryList.size) { i ->
            val item = repositoryList[i]
            if (i >= repositoryList.size - 1 && !screenState.endReached && !screenState.isLoading) {
                loadNextItems.invoke()
            }

            RepositoryListItem(
                repository = item,
                onItemClick = onItemClick,
                onAvatarClick = onAvatarClick
            )
        }
        item {
            if (screenState.isLoading) {
                ProgressIndicator()
            }
        }
    }
}
