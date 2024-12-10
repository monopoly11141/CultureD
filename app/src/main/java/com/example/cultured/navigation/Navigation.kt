package com.example.cultured.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cultured.feature_event.presentation.list.EventListScreenRoot
import com.example.cultured.feature_login.presentation.login.LoginScreenRoot

@Composable
fun Navigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.EventListScreen.route) {
            EventListScreenRoot(
                navController = navHostController
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreenRoot(
                navController = navHostController
            )
        }

    }
}