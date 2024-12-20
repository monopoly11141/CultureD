package com.example.cultured.navigation

sealed class Screen(val route: String) {
    data object EventListScreen: Screen("event_list_screen")

    data object LoginScreen: Screen("login_screen")

    data object CommentWriteScreen: Screen("comment_write_screen")

    data object MapScreen: Screen("map_screen")
}