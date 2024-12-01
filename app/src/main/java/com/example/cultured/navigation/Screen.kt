package com.example.cultured.navigation

sealed class Screen(val route: String) {
    data object EventListScreen: Screen("event_list_screen")
}