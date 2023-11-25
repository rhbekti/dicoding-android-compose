package com.rhbekti.stikacompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Favorite : Screen("favorite")
    object DetailUser : Screen("home/{username}") {
        fun createRoute(username: String) = "home/${username}"
    }
}
