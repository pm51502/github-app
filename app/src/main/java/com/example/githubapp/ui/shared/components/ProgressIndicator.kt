package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = GithubGray,
            strokeWidth = dimensionResource(id = R.dimen.cirular_progress_indicator_width)
        )
    }
}
