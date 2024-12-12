package com.example.cultured.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.createSavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.serialization.generateNavArguments
import androidx.navigation.toRoute
import com.example.cultured.core.presentation.model.EventUiModel
import com.example.cultured.feature_comment.presentation.CommentScreenRoot
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

        composable<EventUiModel> { backStackEntry ->
            val eventUiModel: EventUiModel = backStackEntry.toRoute()
            CommentScreenRoot(
                navController = navHostController,
                eventUiModel = eventUiModel
            )
        }

    }
}