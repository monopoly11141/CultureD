package com.example.cultured.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cultured.event.presentation.list.EventListScreenRoot

@Composable
fun Navigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EventListScreen.route
    ) {
        composable(Screen.EventListScreen.route) {
            EventListScreenRoot(
                navController = navHostController
            )
        }


    }
}