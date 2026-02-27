package cz.gypridilna.inventarizace.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.gypridilna.inventarizace.ui.screens.AccessSystemScreen
import cz.gypridilna.inventarizace.ui.screens.AddItemScreen
import cz.gypridilna.inventarizace.ui.screens.ProfileScreen
import cz.gypridilna.inventarizace.ui.screens.ScannerScreen
import cz.gypridilna.inventarizace.ui.screens.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Scanner,
        Screen.AddItem,
        Screen.Search,
        Screen.AccessSystem,
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon!!, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Scanner.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Scanner.route) { ScannerScreen(navController = navController) }
            composable(Screen.AddItem.route) { AddItemScreen() }
            composable(Screen.Search.route) { SearchScreen(navController = navController) }
            composable(Screen.AccessSystem.route) { AccessSystemScreen() }
            composable(
                route = Screen.Profile.route,
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { backStackEntry ->
                ProfileScreen(itemId = backStackEntry.arguments?.getString("itemId"))
            }
        }
    }
}
