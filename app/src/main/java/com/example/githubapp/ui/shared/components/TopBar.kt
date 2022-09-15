package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.githubapp.R
import com.example.githubapp.ui.navigation.AppScreen
import com.example.githubapp.ui.theme.GithubWhite

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    navController: NavController
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.top_bar_icon_padding))
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = stringResource(id = R.string.github_icon),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.top_bar_icon_size)),
                    tint = White,
                )
            }
        },
        navigationIcon = {
            if (currentRoute != null && currentRoute.startsWith(AppScreen.Details.route)) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowBackIos,
                        contentDescription = stringResource(id = R.string.arrow_back),
                        tint = GithubWhite
                    )
                }
            }
        }
    )
}
