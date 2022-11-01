package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.githubapp.R
import com.example.githubapp.network.Repository

@Composable
fun UserRepositoryCard(
    modifier: Modifier = Modifier,
    repository: Repository,
    onItemClick: (String, String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.user_repository_card_border_radius)),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = dimensionResource(id = R.dimen.user_repository_card_elevation),
        modifier = modifier
            .width(dimensionResource(id = R.dimen.user_repository_card_width))
            .height(dimensionResource(id = R.dimen.user_repository_card_height))
            .clickable { onItemClick.invoke(repository.owner.name, repository.name) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.user_repository_card_vertical_spacing)),
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.user_repository_card_vertical_padding),
                horizontal = dimensionResource(id = R.dimen.user_repository_card_horizontal_padding)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = stringResource(id = R.string.github_icon),
                modifier = Modifier.size(dimensionResource(id = R.dimen.user_repository_card_icon_size))
            )

            Text(
                text = repository.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
