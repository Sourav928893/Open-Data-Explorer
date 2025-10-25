
package com.sourav.opendataexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sourav.opendataexplorer.ui.detail.CategoryDetailScreen
import com.sourav.opendataexplorer.ui.detail.ProductDetailScreen
import com.sourav.opendataexplorer.ui.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            ProductDetailScreen(
                productId = backStackEntry.arguments?.getInt("productId") ?: 0,
            )
        }
        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            CategoryDetailScreen(
                categoryName = backStackEntry.arguments?.getString("categoryName") ?: "",
                navController = navController
            )
        }
    }
}
