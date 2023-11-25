package com.rhbekti.stikacompose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rhbekti.stikacompose.ui.navigation.Screen
import com.rhbekti.stikacompose.ui.screen.about.AboutScreen
import com.rhbekti.stikacompose.ui.screen.detail.DetailUserScreen
import com.rhbekti.stikacompose.ui.screen.favorite.FavoriteScreen
import com.rhbekti.stikacompose.ui.screen.home.HomeScreen
import com.rhbekti.stikacompose.ui.theme.StikaComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StikaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { username ->
                        navController.navigate(Screen.DetailUser.createRoute(username))
                    },
                    navigateToAbout = {
                        navController.navigate(Screen.About.route)
                    },
                    navigateToFavorite = {
                        navController.navigate(Screen.Favorite.route)
                    }
                )
            }
            composable(
                route = Screen.DetailUser.route,
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) {
                val username = it.arguments?.getString("username") ?: ""
                DetailUserScreen(
                    username = username,
                    navigateOnBack = { navController.navigateUp() }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(
                    navigateToBack = { navController.navigateUp() },
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateOnBack = { navController.navigateUp() }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StikaAppPreview() {
    StikaComposeTheme {
        StikaApp()
    }
}