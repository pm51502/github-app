package com.example.githubapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailsScreen(
    navController: NavController,
    repositoryId: Int?,
) {
    Column {
        Text(text = "Details screen")

        if (repositoryId != null)
            Text(text = "Repo id: $repositoryId")

        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "To home screen")
        }
    }
}
