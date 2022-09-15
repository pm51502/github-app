package com.example.githubapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.example.githubapp.R
import com.example.githubapp.ui.navigation.navigateToScreen
import com.example.githubapp.ui.shared.components.SearchBar

@Composable
fun HomeScreen(
    navController: NavController
) {
    val showSearchBar by remember { mutableStateOf(false) }
    val showFilterPicker by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        //screen header

        SearchBar(
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier.padding(dimensionResource(id = R.dimen.search_bar_padding))
        )

        Text(text = searchQuery)

        Button(onClick = {
            navigateToScreen(
                navController = navController,
                route = "details/15"
            )
        }) {
            Text(text = "To details screen")
        }
    }
}
