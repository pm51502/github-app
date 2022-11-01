package com.example.githubapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubapp.ui.screens.DetailsScreen
import com.example.githubapp.ui.screens.HomeScreen

sealed class AppScreen(val route: String) {
    object Home : AppScreen(route = RouterConstants.HOME_ROUTE)
    object Details : AppScreen(route = RouterConstants.DETAILS_ROUTE)
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
            route = "${AppScreen.Details.route}/{${RouterConstants.REPO_OWNER}}/{${RouterConstants.REPO_NAME}}",
            arguments = listOf(
                navArgument(RouterConstants.REPO_OWNER) { type = NavType.StringType },
                navArgument(RouterConstants.REPO_NAME) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
                repoOwner = backStackEntry.arguments?.getString(RouterConstants.REPO_OWNER),
                repoName = backStackEntry.arguments?.getString(RouterConstants.REPO_NAME)
            )
        }
    }
}

object RouterConstants {
    const val HOME_ROUTE = "home"
    const val DETAILS_ROUTE = "details"
    const val REPO_OWNER = "repoOwner"
    const val REPO_NAME = "repoName"
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
