package com.example.cultured.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cultured.culture_event.presentation.list.CultureEventListScreenRoot

@Composable
fun Navigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CultureEventListScreen.route
    ) {
        composable(Screen.CultureEventListScreen.route) {
            CultureEventListScreenRoot(
                navController = navHostController
            )
        }


    }
}