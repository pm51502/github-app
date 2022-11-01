package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.githubapp.R
import com.example.githubapp.data.DetailsScreenState
import com.example.githubapp.network.Repository

@Composable
fun UserRepositoryList(
    modifier: Modifier = Modifier,
    userRepositoryList: List<Repository>,
    onItemClick: (String, String) -> Unit,
    loadNextItems: () -> Unit,
    screenState: DetailsScreenState,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.user_repository_list_spacing)),
    ) {
        items(userRepositoryList.size) { i ->
            val item = userRepositoryList[i]
            if (i >= userRepositoryList.size - 1 && !screenState.endReached && !screenState.isLoadingItems) {
                loadNextItems.invoke()
            }

            UserRepositoryCard(
                repository = item,
                onItemClick = onItemClick
            )
        }
        item {
            if (screenState.isLoadingItems) {
                ProgressIndicator()
            }
        }
    }
}
