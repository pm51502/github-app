package com.example.githubapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubapp.R
import com.example.githubapp.ui.screens.DetailsScreen
import com.example.githubapp.ui.screens.HomeScreen

sealed class AppScreen(val route: String, @StringRes val resourceId: Int) {
    object Home : AppScreen(route = "home", R.string.home)
    object Details : AppScreen(route = "details", R.string.details)
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(
            route = AppScreen.Home.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = "${AppScreen.Details.route}/{repoId}",
            arguments = listOf(navArgument("repoId") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
                repositoryId = backStackEntry.arguments?.getInt("repoId")
            )
        }
    }
}

fun navigateToScreen(navController: NavController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
