package com.example.cultured.navigation

sealed class Screen(val route: String) {
    data object CultureEventListScreen: Screen("culture_event_list_screen")
}